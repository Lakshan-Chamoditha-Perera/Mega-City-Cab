<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Megacity Cab Service - Dashboard</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.11.1/font/bootstrap-icons.min.css"
          rel="stylesheet">
    <style>
        :root {
            --primary-color: #0d6efd;
            --secondary-color: #6c757d;
            --success-color: #198754;
            --warning-color: #ffc107;
            --danger-color: #dc3545;
            --background-color: #f8f9fa;
            --hover-bg-color: rgba(13, 110, 253, 0.05);
        }

        body {
            background-color: var(--background-color);
            min-height: 100vh;
        }

        .card {
            border: none;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            border-radius: 1rem;
            transition: transform 0.2s;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .section-title {
            color: #212529;
            font-weight: 600;
            margin-bottom: 1.5rem;
            font-size: 24px;
            display: flex;
            align-items: center;
        }

        .section-title i {
            font-size: 28px;
            color: var(--primary-color);
            margin-right: 0.5rem;
        }

        .stat-card {
            background: white;
            padding: 1.5rem;
        }

        .stat-card .stat-icon {
            font-size: 2.5rem;
            color: var(--primary-color);
        }

        .stat-card .stat-value {
            font-size: 2rem;
            font-weight: bold;
            color: #212529;
            margin: 0.5rem 0;
        }

        .stat-card .stat-label {
            color: var(--secondary-color);
            font-size: 1rem;
        }

        .activity-card {
            padding: 0;
        }

        .activity-item {
            display: flex;
            align-items: center;
            padding: 1rem;
            border-bottom: 1px solid #e9ecef;
        }

        .activity-item:last-child {
            border-bottom: none;
        }

        .activity-icon {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 1rem;
            font-size: 1.2rem;
        }

        .activity-details h6 {
            margin: 0;
            color: #212529;
        }

        .activity-details p {
            margin: 0;
            color: var(--secondary-color);
            font-size: 0.875rem;
        }

        .quick-actions {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1rem;
        }

        .action-card {
            text-align: center;
            padding: 1.5rem;
            cursor: pointer;
        }

        .action-card i {
            font-size: 2rem;
            margin-bottom: 0.5rem;
        }
    </style>
</head>
<body class="py-4">
<!-- navbar.jsp -->
<nav class="navbar navbar-expand-lg sticky-top">
    <style>
        .navbar {
            background-color: white;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
            padding: 1rem 1.5rem;
        }

        .navbar-brand {
            font-weight: 600;
            color: var(--primary-color) !important;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .navbar-nav {
            gap: 0.5rem;
        }

        .nav-link {
            color: var(--secondary-color) !important;
            font-weight: 500;
            padding: 0.5rem 1rem !important;
            border-radius: 0.5rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            cursor: pointer;
        }

        .nav-link:hover, .nav-link.active {
            color: var(--primary-color) !important;
            background-color: var(--hover-bg-color);
        }

        .nav-link i {
            font-size: 1.25rem;
        }

        .auth-buttons {
            display: flex;
            gap: 0.5rem;
        }

        .navbar-toggler {
            border: none;
            padding: 0.5rem;
        }

        .navbar-toggler:focus {
            box-shadow: none;
        }

        @media (max-width: 991.98px) {
            .navbar-nav {
                padding: 1rem 0;
            }

            .auth-buttons {
                padding: 1rem 0;
                border-top: 1px solid var(--hover-bg-color);
                margin-top: 0.5rem;
            }
        }
    </style>

    <div class="container-fluid">
        <!-- Logo and Brand -->
        <a class="navbar-brand" href="#" onclick="navigate('app')">
            <i class="bi bi-car-front-fill"></i>
            Megacity Cab Service
        </a>

        <!-- Mobile Toggle Button -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent"
                aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
            <i class="bi bi-list"></i>
        </button>

        <!-- Navigation Items -->
        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/home" method="get">
                        <button class="nav-link" type="submit" id="nav-dashboard">
                            <i class="bi bi-speedometer2"></i>
                            Dashboard
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/customers" method="get">
                        <button class="nav-link" type="submit" id="nav-customers">
                            <i class="bi bi-people"></i>
                            Customers
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/vehicles" method="get">
                        <button class="nav-link" type="submit" id="nav-vehicles">
                            <i class="bi bi-car-front"></i>
                            Vehicles
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/drivers" method="get">
                        <button class="nav-link" type="submit" id="nav-drivers">
                            <i class="bi bi-person-badge"></i>
                            Drivers
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/bookings" method="get">
                        <button class="nav-link" type="submit" id="nav-bookings">
                            <i class="bi bi-calendar-check"></i>
                            Bookings
                        </button>
                    </form>
                </li>
            </ul>

            <!-- Auth Buttons -->
            <div class="auth-buttons">
                <% if (session.getAttribute("user") == null) { %>
                <form action="${pageContext.request.contextPath}/login" method="get">
                    <button type="submit" class="btn btn-outline-primary">
                        <i class="bi bi-box-arrow-in-right"></i>
                        Login
                    </button>
                </form>
                <% } else { %>
                <form action="${pageContext.request.contextPath}/logout" method="get">
                    <button type="submit" class="btn btn-outline-danger">
                        <i class="bi bi-box-arrow-right"></i>
                        Logout
                    </button>
                </form>
                <% } %>
            </div>
        </div>
    </div>

    <script>
        // Set active nav item based on current path
        document.addEventListener('DOMContentLoaded', function() {
            const currentPath = window.location.pathname;
            const navItems = {
                'app': 'nav-dashboard',
                'customers': 'nav-customers',
                'vehicles': 'nav-vehicles',
                'drivers': 'nav-drivers',
                'bookings': 'nav-bookings'
            };

            // Remove all active classes first
            Object.values(navItems).forEach(id => {
                document.getElementById(id)?.classList.remove('active');
            });

            // Add active class to current nav item
            for (const [path, id] of Object.entries(navItems)) {
                if (currentPath.includes(path)) {
                    document.getElementById(id)?.classList.add('active');
                    break;
                }
            }
        });
    </script>
</nav>


<div class="container">
    <!-- Welcome Section -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="section-title">
            <i class="bi bi-speedometer2"></i>
            Dashboard Overview
        </h1>
        <button class="btn btn-primary">
            <i class="bi bi-plus-circle me-2"></i>New Booking
        </button>
    </div>

    <!-- Statistics Cards -->
    <div class="row g-4 mb-4">
        <div class="col-md-3">
            <div class="card stat-card">
                <div class="d-flex justify-content-between">
                    <div>
                        <div class="stat-value">120</div>
                        <div class="stat-label">Total Customers</div>
                    </div>
                    <i class="bi bi-people stat-icon"></i>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card">
                <div class="d-flex justify-content-between">
                    <div>
                        <div class="stat-value">50</div>
                        <div class="stat-label">Total Vehicles</div>
                    </div>
                    <i class="bi bi-car-front stat-icon"></i>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card">
                <div class="d-flex justify-content-between">
                    <div>
                        <div class="stat-value">45</div>
                        <div class="stat-label">Total Drivers</div>
                    </div>
                    <i class="bi bi-person-badge stat-icon"></i>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card">
                <div class="d-flex justify-content-between">
                    <div>
                        <div class="stat-value">300</div>
                        <div class="stat-label">Total Bookings</div>
                    </div>
                    <i class="bi bi-calendar-check stat-icon"></i>
                </div>
            </div>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="card mb-4">
        <div class="card-body">
            <h2 class="section-title">
                <i class="bi bi-lightning"></i>
                Quick Actions
            </h2>
            <div class="quick-actions">
                <div class="card action-card">
                    <i class="bi bi-person-plus text-primary"></i>
                    <h5>Add Customer</h5>
                </div>
                <div class="card action-card">
                    <i class="bi bi-car-front-fill text-success"></i>
                    <h5>Add Vehicle</h5>
                </div>
                <div class="card action-card">
                    <i class="bi bi-person-badge-fill text-warning"></i>
                    <h5>Add Driver</h5>
                </div>
                <div class="card action-card">
                    <i class="bi bi-calendar-plus text-info"></i>
                    <h5>New Booking</h5>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4">
        <!-- Recent Bookings -->
        <div class="col-md-8">
            <div class="card">
                <div class="card-body">
                    <h2 class="section-title">
                        <i class="bi bi-activity"></i>
                        Recent Bookings
                    </h2>
                    <div class="activity-card">
                        <div class="activity-item">
                            <div class="activity-icon bg-primary bg-opacity-10 text-primary">
                                <i class="bi bi-calendar-check"></i>
                            </div>
                            <div class="activity-details">
                                <h6>New Booking Created</h6>
                                <p>John Doe booked a cab for tomorrow</p>
                            </div>
                        </div>
                        <div class="activity-item">
                            <div class="activity-icon bg-success bg-opacity-10 text-success">
                                <i class="bi bi-car-front"></i>
                            </div>
                            <div class="activity-details">
                                <h6>Vehicle Added</h6>
                                <p>New vehicle #1234 added to fleet</p>
                            </div>
                        </div>
                        <div class="activity-item">
                            <div class="activity-icon bg-warning bg-opacity-10 text-warning">
                                <i class="bi bi-person-badge"></i>
                            </div>
                            <div class="activity-details">
                                <h6>Driver Update</h6>
                                <p>Driver Jane Smith profile updated</p>
                            </div>
                        </div>
                        <div class="activity-item">
                            <div class="activity-icon bg-info bg-opacity-10 text-info">
                                <i class="bi bi-person-plus"></i>
                            </div>
                            <div class="activity-details">
                                <h6>New Customer</h6>
                                <p>Michael Brown registered as new customer</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Summary Stats -->
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h2 class="section-title">
                        <i class="bi bi-graph-up"></i>
                        Quick Stats
                    </h2>
                    <div class="d-flex flex-column gap-3">
                        <div class="d-flex justify-content-between align-items-center">
                            <span>Active Bookings</span>
                            <span class="badge bg-primary">24</span>
                        </div>
                        <div class="d-flex justify-content-between align-items-center">
                            <span>Available Vehicles</span>
                            <span class="badge bg-success">15</span>
                        </div>
                        <div class="d-flex justify-content-between align-items-center">
                            <span>On-Duty Drivers</span>
                            <span class="badge bg-warning">18</span>
                        </div>
                        <div class="d-flex justify-content-between align-items-center">
                            <span>Pending Requests</span>
                            <span class="badge bg-danger">7</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
    // Base URL for the application
    const baseUrl = '${pageContext.request.contextPath}';

    // Navigation function
    function navigate(path) {
        const url = `${baseUrl}/${path}?action=GET`;
        window.location.href = url;
    }

    // Logout handler
    function handleLogout() {
        // You can add any cleanup or session termination logic here
        navigate('logout');
    }

    // Set active nav item based on current path
    document.addEventListener('DOMContentLoaded', function() {
        const currentPath = window.location.pathname;
        const navItems = {
            'app': 'nav-dashboard',
            'customers': 'nav-customers',
            'vehicles': 'nav-vehicles',
            'drivers': 'nav-drivers',
            'bookings': 'nav-bookings'
        };

        // Remove all active classes first
        Object.values(navItems).forEach(id => {
            document.getElementById(id)?.classList.remove('active');
        });

        // Add active class to current nav item
        for (const [path, id] of Object.entries(navItems)) {
            if (currentPath.includes(path)) {
                document.getElementById(id)?.classList.add('active');
                break;
            }
        }
    });
</script>
</body>
</html>