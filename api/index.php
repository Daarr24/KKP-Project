<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type, Authorization');

// Handle preflight requests
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit();
}

// Database configuration
$host = 'localhost';
$dbname = 'management-asset';
$username = 'root'; // Username database Hostinger
$password = ''; // Password database Hostinger

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8mb4", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch(PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => 'Database connection failed: ' . $e->getMessage()]);
    exit();
}

// Get request method and path
$method = $_SERVER['REQUEST_METHOD'];
$path = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
$path = str_replace('/api/', '', $path);

// Simple authentication check
function checkAuth() {
    $headers = getallheaders();
    $token = isset($headers['Authorization']) ? str_replace('Bearer ', '', $headers['Authorization']) : null;
    
    // For demo purposes, accept any token
    // In production, validate the token properly
    return !empty($token);
}

// Route handling
switch($method) {
    case 'POST':
        if ($path === 'login') {
            handleLogin($pdo);
        } else {
            http_response_code(404);
            echo json_encode(['error' => 'Endpoint not found']);
        }
        break;
        
    case 'GET':
        if (strpos($path, 'assets') !== false) {
            handleGetAssets($pdo, $path);
        } elseif (strpos($path, 'detail-assets') !== false) {
            handleGetDetailAssets($pdo, $path);
        } elseif (strpos($path, 'projects') !== false) {
            handleGetProjects($pdo, $path);
        } elseif (strpos($path, 'pengiriman') !== false) {
            handleGetPengiriman($pdo, $path);
        } elseif (strpos($path, 'tagihan') !== false) {
            handleGetTagihan($pdo, $path);
        } elseif (strpos($path, 'rental') !== false) {
            handleGetRental($pdo, $path);
        } else {
            http_response_code(404);
            echo json_encode(['error' => 'Endpoint not found']);
        }
        break;
        
    default:
        http_response_code(405);
        echo json_encode(['error' => 'Method not allowed']);
        break;
}

function handleLogin($pdo) {
    $input = json_decode(file_get_contents('php://input'), true);
    $email = $input['email'] ?? '';
    $password = $input['password'] ?? '';
    
    if (empty($email) || empty($password)) {
        http_response_code(400);
        echo json_encode(['error' => 'Email and password are required']);
        return;
    }
    
    try {
        $stmt = $pdo->prepare("SELECT * FROM users WHERE email = ?");
        $stmt->execute([$email]);
        $user = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if ($user && password_verify($password, $user['password'])) {
            // Generate a simple token (in production, use JWT)
            $token = bin2hex(random_bytes(32));
            
            echo json_encode([
                'success' => true,
                'message' => 'Login successful',
                'user' => [
                    'id' => $user['id'],
                    'name' => $user['name'],
                    'email' => $user['email'],
                    'email_verified_at' => $user['email_verified_at'],
                    'created_at' => $user['created_at'],
                    'updated_at' => $user['updated_at']
                ],
                'token' => $token
            ]);
        } else {
            http_response_code(401);
            echo json_encode([
                'success' => false,
                'message' => 'Invalid credentials'
            ]);
        }
    } catch(PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function handleGetAssets($pdo, $path) {
    if (!checkAuth()) {
        http_response_code(401);
        echo json_encode(['error' => 'Unauthorized']);
        return;
    }
    
    try {
        if (preg_match('/assets\/(\d+)/', $path, $matches)) {
            // Get specific asset
            $id = $matches[1];
            $stmt = $pdo->prepare("SELECT * FROM assets WHERE id = ?");
            $stmt->execute([$id]);
            $asset = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if ($asset) {
                echo json_encode($asset);
            } else {
                http_response_code(404);
                echo json_encode(['error' => 'Asset not found']);
            }
        } else {
            // Get all assets
            $stmt = $pdo->query("SELECT * FROM assets ORDER BY created_at DESC");
            $assets = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($assets);
        }
    } catch(PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function handleGetDetailAssets($pdo, $path) {
    if (!checkAuth()) {
        http_response_code(401);
        echo json_encode(['error' => 'Unauthorized']);
        return;
    }
    
    try {
        if (preg_match('/detail-assets\/(\d+)/', $path, $matches)) {
            // Get specific detail asset
            $id = $matches[1];
            $stmt = $pdo->prepare("
                SELECT da.*, a.merk, a.type, a.spesifikasi 
                FROM detailasset da 
                LEFT JOIN assets a ON da.asset_id = a.id 
                WHERE da.id = ?
            ");
            $stmt->execute([$id]);
            $detailAsset = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if ($detailAsset) {
                echo json_encode($detailAsset);
            } else {
                http_response_code(404);
                echo json_encode(['error' => 'Detail asset not found']);
            }
        } else {
            // Get all detail assets
            $stmt = $pdo->query("
                SELECT da.*, a.merk, a.type, a.spesifikasi 
                FROM detailasset da 
                LEFT JOIN assets a ON da.asset_id = a.id 
                ORDER BY da.created_at DESC
            ");
            $detailAssets = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($detailAssets);
        }
    } catch(PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function handleGetProjects($pdo, $path) {
    if (!checkAuth()) {
        http_response_code(401);
        echo json_encode(['error' => 'Unauthorized']);
        return;
    }
    
    try {
        if (preg_match('/projects\/(\d+)/', $path, $matches)) {
            // Get specific project
            $id = $matches[1];
            $stmt = $pdo->prepare("SELECT * FROM projects WHERE id = ?");
            $stmt->execute([$id]);
            $project = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if ($project) {
                echo json_encode($project);
            } else {
                http_response_code(404);
                echo json_encode(['error' => 'Project not found']);
            }
        } else {
            // Get all projects
            $stmt = $pdo->query("SELECT * FROM projects ORDER BY created_at DESC");
            $projects = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($projects);
        }
    } catch(PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function handleGetPengiriman($pdo, $path) {
    if (!checkAuth()) {
        http_response_code(401);
        echo json_encode(['error' => 'Unauthorized']);
        return;
    }
    
    try {
        if (preg_match('/pengiriman\/(\d+)/', $path, $matches)) {
            // Get specific pengiriman
            $id = $matches[1];
            $stmt = $pdo->prepare("
                SELECT p.*, da.serialnumber, a.merk, a.type 
                FROM pengiriman p 
                LEFT JOIN detailasset da ON p.detailasset_id = da.id 
                LEFT JOIN assets a ON da.asset_id = a.id 
                WHERE p.id = ?
            ");
            $stmt->execute([$id]);
            $pengiriman = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if ($pengiriman) {
                echo json_encode($pengiriman);
            } else {
                http_response_code(404);
                echo json_encode(['error' => 'Pengiriman not found']);
            }
        } else {
            // Get all pengiriman
            $stmt = $pdo->query("
                SELECT p.*, da.serialnumber, a.merk, a.type 
                FROM pengiriman p 
                LEFT JOIN detailasset da ON p.detailasset_id = da.id 
                LEFT JOIN assets a ON da.asset_id = a.id 
                ORDER BY p.created_at DESC
            ");
            $pengiriman = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($pengiriman);
        }
    } catch(PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function handleGetTagihan($pdo, $path) {
    if (!checkAuth()) {
        http_response_code(401);
        echo json_encode(['error' => 'Unauthorized']);
        return;
    }
    
    try {
        if (preg_match('/tagihan\/(\d+)/', $path, $matches)) {
            // Get specific tagihan
            $id = $matches[1];
            $stmt = $pdo->prepare("SELECT * FROM tagihan WHERE id = ?");
            $stmt->execute([$id]);
            $tagihan = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if ($tagihan) {
                echo json_encode($tagihan);
            } else {
                http_response_code(404);
                echo json_encode(['error' => 'Tagihan not found']);
            }
        } else {
            // Get all tagihan
            $stmt = $pdo->query("SELECT * FROM tagihan ORDER BY created_at DESC");
            $tagihan = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($tagihan);
        }
    } catch(PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function handleGetRental($pdo, $path) {
    if (!checkAuth()) {
        http_response_code(401);
        echo json_encode(['error' => 'Unauthorized']);
        return;
    }
    
    try {
        if (preg_match('/rental\/(\d+)/', $path, $matches)) {
            // Get specific rental
            $id = $matches[1];
            $stmt = $pdo->prepare("
                SELECT r.*, p.nama as project_nama, t.nomor_invoice, t.keterangan,
                       peng.tanggal_pengiriman, peng.pic_pengirim, peng.pic_penerima
                FROM rental r 
                LEFT JOIN projects p ON r.project_id = p.id 
                LEFT JOIN tagihan t ON r.tagihan_id = t.id 
                LEFT JOIN pengiriman peng ON r.pengiriman_id = peng.id 
                WHERE r.id = ?
            ");
            $stmt->execute([$id]);
            $rental = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if ($rental) {
                echo json_encode($rental);
            } else {
                http_response_code(404);
                echo json_encode(['error' => 'Rental not found']);
            }
        } else {
            // Get all rental
            $stmt = $pdo->query("
                SELECT r.*, p.nama as project_nama, t.nomor_invoice, t.keterangan,
                       peng.tanggal_pengiriman, peng.pic_pengirim, peng.pic_penerima
                FROM rental r 
                LEFT JOIN projects p ON r.project_id = p.id 
                LEFT JOIN tagihan t ON r.tagihan_id = t.id 
                LEFT JOIN pengiriman peng ON r.pengiriman_id = peng.id 
                ORDER BY r.created_at DESC
            ");
            $rental = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($rental);
        }
    } catch(PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}
?> 