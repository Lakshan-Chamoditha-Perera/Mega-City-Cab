<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Megacity Cab Service - Admin Dashboard</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.11.1/font/bootstrap-icons.min.css"
          rel="stylesheet">
    <style>
        ::-webkit-scrollbar {
            display: none;
        }

        * {
            scrollbar-width: none;
        }

        :root {
            --primary-color: #0d6efd;
            --secondary-color: #6c757d;
            --success-color: #198754;
            --warning-color: #ffc107;
            --danger-color: #dc3545;
            --info-color: #0dcaf0;
            --background-color: #f8f9fa;
            --hover-bg-color: rgba(13, 110, 253, 0.05);
            --light-bg-color: #ffffff;
            --dark-text-color: #212529;
            --border-color: #e9ecef;
        }

        body {
            background-color: var(--background-color);
            min-height: 100vh;
            padding: 0 10.33vw;
        }

        .card {
            border: none;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            border-radius: 1rem;
            transition: transform 0.2s;
            margin-bottom: 1.5rem;
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

        .stat-card .stat-change {
            font-size: 0.875rem;
            display: flex;
            align-items: center;
            margin-top: 0.5rem;
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
            transition: all 0.3s ease;
        }

        .action-card:hover {
            background-color: var(--hover-bg-color);
        }

        .action-card i {
            font-size: 2rem;
            margin-bottom: 0.5rem;
        }

        .status-pill {
            padding: 0.25rem 0.75rem;
            border-radius: 50px;
            font-size: 0.75rem;
            font-weight: 600;
            text-transform: uppercase;
        }

        .status-pending {
            background-color: var(--warning-color);
            color: #000;
        }

        .status-confirmed {
            background-color: var(--success-color);
            color: #fff;
        }

        .status-canceled {
            background-color: var(--danger-color);
            color: #fff;
        }

        .today-bookings {
            border-left: 4px solid var(--primary-color);
            padding-left: 1rem;
        }

        .revenue-card {
            background-color: var(--primary-color);
            color: white;
        }

        .revenue-card .stat-label,
        .revenue-card .stat-value {
            color: white;
        }

        .progress-thin {
            height: 6px;
            border-radius: 3px;
        }

        .booking-time {
            font-size: 0.8rem;
            color: var(--secondary-color);
        }

        .table-responsive {
            border-radius: 0.5rem;
            overflow: hidden;
        }

        .custom-table {
            margin-bottom: 0;
        }

        .custom-table th {
            background-color: rgba(13, 110, 253, 0.1);
            font-weight: 600;
            color: var(--primary-color);
        }

        .custom-table tr:nth-child(even) {
            background-color: rgba(0, 0, 0, 0.02);
        }

        .data-card {
            height: 100%;
            display: flex;
            flex-direction: column;
        }

        .data-card .card-body {
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        .data-card .table-container {
            flex: 1;
            overflow-y: auto;
            max-height: 300px;
        }

        .info-box {
            padding: 1rem;
            border-radius: 0.5rem;
            margin-bottom: 1rem;
        }

        .info-box.alert-primary {
            background-color: rgba(13, 110, 253, 0.1);
            border-left: 4px solid var(--primary-color);
        }

        .info-box.alert-warning {
            background-color: rgba(255, 193, 7, 0.1);
            border-left: 4px solid var(--warning-color);
        }

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
</head>
<body class="">
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
        <a class="navbar-brand" onclick="navigate('app')">
            <i class="bi bi-car-front-fill"></i>
            Megacity Cab Service
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent"
                aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
            <i class="bi bi-list"></i>
        </button>

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
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/reports" method="get">
                        <button class="nav-link" type="submit" id="nav-reports">
                            <i class="bi bi-calendar-check"></i>
                            Sales & Reports
                        </button>
                    </form>
                </li>
            </ul>

            <!-- Auth Buttons -->
            <div class="auth-buttons">
                <% if (session.getAttribute("userId") == null) { %>
                <form action="${pageContext.request.contextPath}/login" method="get">
                    <button type="submit" class="btn btn-outline-primary">
                        <i class="bi bi-box-arrow-in-right"></i>
                        Login
                    </button>
                </form>
                <% } else { %>
                <form action="${pageContext.request.contextPath}/auth/logout" method="post">
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
        document.addEventListener('DOMContentLoaded', function () {
            const currentPath = window.location.pathname;
            const navItems = {
                'app': 'nav-dashboard',
                'customers': 'nav-customers',
                'vehicles': 'nav-vehicles',
                'drivers': 'nav-drivers',
                'bookings': 'nav-bookings',
                'reports': 'nav-reports'
            };

            Object.values(navItems).forEach(id => {
                document.getElementById(id)?.classList.remove('active');
            });

            for (const [path, id] of Object.entries(navItems)) {
                if (currentPath.includes(path)) {
                    document.getElementById(id)?.classList.add('active');
                    break;
                }
            }
        });
    </script>
</nav>


<div class="container-fluid py-4">

    <div class="row mb-4">
        <div class="col-md-8">
            <h1 class="section-title">
                <i class="bi bi-speedometer2"></i>
                Dashboard Overview
            </h1>
            <p class="text-muted mb-0">Welcome to the Megacity Cab Service admin panel. Here's what's happening
                today.</p>
        </div>
        <div class="col-md-4 text-end">
            <div class="btn-group">
                <button class="btn btn-primary"
                        onclick="location.href='${pageContext.request.contextPath}/bookings?action=new'">
                    <i class="bi bi-plus-circle me-2"></i>New Booking
                </button>
            </div>
        </div>
    </div>

    <div class="info-box alert-primary mb-4">
        <h5><i class="bi bi-calendar-check me-2"></i>Today: <fmt:formatDate value="<%= new java.util.Date() %>"
                                                                            pattern="EEEE, MMMM d, yyyy"/></h5>
        <p class="mb-0">
            <i class="bi bi-clock me-2"></i>Current Time: <span id="currentTime"></span> |
            <i class="bi bi-lightning me-2"></i>System Status: <span class="badge bg-success">Online</span>
        </p>
    </div>

    <div class="row g-4 mb-4">
        <div class="col-md-3 col-sm-6">
            <div class="card stat-card">
                <div class="d-flex justify-content-between">
                    <div>
                        <div class="stat-value">
                            ${customersCount}
                        </div>
                        <div class="stat-label">Total Customers</div>
                        <div class="stat-change text-success">
                            <i class="bi bi-arrow-up-circle me-1"></i>
                            <c:choose>
                                <c:when test="${customerGrowth != null}">${customerGrowth}%</c:when>
                                <c:otherwise>2.5%</c:otherwise>
                            </c:choose>
                            this month
                        </div>
                    </div>
                    <i class="bi bi-people stat-icon text-primary"></i>
                </div>
            </div>
        </div>
        <div class="col-md-3 col-sm-6">
            <div class="card stat-card">
                <div class="d-flex justify-content-between">
                    <div>
                        <div class="stat-value">
                            ${vehiclesCount}
                        </div>
                        <div class="stat-label">Fleet Size</div>
                        <div class="d-flex align-items-center mt-2">
                            <span class="badge bg-success me-2">
                                <c:choose>
                                    <c:when test="${availableVehicles != null}">${availableVehicles}</c:when>
                                    <c:otherwise>0</c:otherwise>
                                </c:choose>
                                Available
                            </span>
                            <span class="badge bg-warning">
                                <c:choose>
                                    <c:when test="${busyVehicles != null}">${busyVehicles}</c:when>
                                    <c:otherwise>${vehiclesCount - 15}</c:otherwise>
                                </c:choose>
                                In Service
                            </span>
                        </div>
                    </div>
                    <i class="bi bi-car-front stat-icon text-success"></i>
                </div>
            </div>
        </div>
        <div class="col-md-3 col-sm-6">
            <div class="card stat-card">
                <div class="d-flex justify-content-between">
                    <div>
                        <div class="stat-value">
                            ${driversCount}
                        </div>
                        <div class="stat-label">Active Drivers</div>
                        <div class="d-flex align-items-center mt-2">
                            <span class="badge bg-success me-2">
                                <c:choose>
                                    <c:when test="${availableDrivers != null}">${availableDrivers}</c:when>
                                    <c:otherwise>18</c:otherwise>
                                </c:choose>
                                On Duty
                            </span>
                            <span class="badge bg-secondary">
                                <c:choose>
                                    <c:when test="${offDutyDrivers != null}">${offDutyDrivers}</c:when>
                                    <c:otherwise>${driversCount - 18}</c:otherwise>
                                </c:choose>
                                Off Duty
                            </span>
                        </div>
                    </div>
                    <i class="bi bi-person-badge stat-icon text-warning"></i>
                </div>
            </div>
        </div>
        <div class="col-md-3 col-sm-6">
            <div class="card stat-card revenue-card">
                <div class="d-flex justify-content-between">
                    <div>
                        <div class="stat-value">
                            $<c:choose>
                            <c:when test="${bookingStats.totalRevenue != null}">
                                <fmt:formatNumber value="${bookingStats.totalRevenue}" pattern="#,##0.00"/>
                            </c:when>
                            <c:otherwise>10,000.00</c:otherwise>
                        </c:choose>
                        </div>
                        <div class="stat-label">Total Revenue</div>
                        <div class="stat-change">
                            <i class="bi bi-arrow-up-circle me-1"></i>
                            All Time
                        </div>
                    </div>
                    <i class="bi bi-currency-dollar stat-icon"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4 mb-4">
        <div class="col-md-8">
            <div class="card">
                <div class="card-body">
                    <h2 class="section-title">
                        <i class="bi bi-graph-up"></i>
                        Booking Status
                    </h2>
                    <div class="row g-4">
                        <div class="col-md-4">
                            <div class="text-center mb-2">
                                <h3 class="text-primary mb-0" id="pendingCount">0</h3>
                                <p class="text-muted">Pending</p>
                            </div>
                            <div class="progress progress-thin">
                                <div class="progress-bar bg-primary" id="pendingBar" role="progressbar"
                                     style="width: 0%;" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="text-center mb-2">
                                <h3 class="text-success mb-0" id="confirmedCount">0</h3>
                                <p class="text-muted">Confirmed</p>
                            </div>
                            <div class="progress progress-thin">
                                <div class="progress-bar bg-success" id="confirmedBar" role="progressbar"
                                     style="width: 0%;" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="text-center mb-2">
                                <h3 class="text-danger mb-0" id="cancelledCount">0</h3>
                                <p class="text-muted">Canceled</p>
                            </div>
                            <div class="progress progress-thin">
                                <div class="progress-bar bg-danger" id="cancelledBar" role="progressbar"
                                     style="width: 0%;" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h2 class="section-title">
                        <i class="bi bi-calendar-day"></i>
                        Today's Bookings
                    </h2>
                    <h3 class="text-center mb-3" id="totalBookings">0</h3>
                    <div class="progress mb-3" style="height: 20px;">
                        <div class="progress-bar bg-primary" id="totalPendingBar" role="progressbar" style="width: 0%;"
                             aria-valuemin="0" aria-valuemax="100"></div>
                        <div class="progress-bar bg-success" id="totalConfirmedBar" role="progressbar"
                             style="width: 0%;" aria-valuemin="0" aria-valuemax="100"></div>
                        <div class="progress-bar bg-danger" id="totalCancelledBar" role="progressbar" style="width: 0%;"
                             aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    <div class="d-flex justify-content-around text-center">
                        <div>
                            <div class="badge bg-primary">Pending</div>
                            <div class="mt-1" id="badgePending">0</div>
                        </div>
                        <div>
                            <div class="badge bg-success">Confirmed</div>
                            <div class="mt-1" id="badgeConfirmed">0</div>
                        </div>
                        <div>
                            <div class="badge bg-danger">Canceled</div>
                            <div class="mt-1" id="badgeCancelled">0</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const pending = ${bookingStats.pendingBookings != null ? bookingStats.pendingBookings : 7};
            const confirmed = ${bookingStats.confirmedBookings != null ? bookingStats.confirmedBookings : 24};
            const cancelled = ${bookingStats.cancelledBookings != null ? bookingStats.cancelledBookings : 1};
            const total = pending + confirmed + cancelled;

            document.getElementById("pendingCount").innerText = pending;
            document.getElementById("confirmedCount").innerText = confirmed;
            document.getElementById("cancelledCount").innerText = cancelled;
            document.getElementById("totalBookings").innerText = total;

            document.getElementById("badgePending").innerText = pending;
            document.getElementById("badgeConfirmed").innerText = confirmed;
            document.getElementById("badgeCancelled").innerText = cancelled;

            const pendingPercent = total > 0 ? (pending / total) * 100 : 0;
            const confirmedPercent = total > 0 ? (confirmed / total) * 100 : 0;
            const cancelledPercent = total > 0 ? (cancelled / total) * 100 : 0;

            document.getElementById("pendingBar").style.width = pendingPercent + "%";
            document.getElementById("confirmedBar").style.width = confirmedPercent + "%";
            document.getElementById("cancelledBar").style.width = cancelledPercent + "%";

            document.getElementById("totalPendingBar").style.width = pendingPercent + "%";
            document.getElementById("totalConfirmedBar").style.width = confirmedPercent + "%";
            document.getElementById("totalCancelledBar").style.width = cancelledPercent + "%";
        });
    </script>


    <!-- Quick Actions Section -->
    <div class="card mb-4">
        <div class="card-body">
            <h2 class="section-title">
                <i class="bi bi-lightning"></i>
                Quick Actions
            </h2>
            <div class="quick-actions">
                <div class="card action-card"
                     onclick="location.href='${pageContext.request.contextPath}/customers?action=new'">
                    <i class="bi bi-person-plus text-primary"></i>
                    <h5>Add Customer</h5>
                </div>
                <div class="card action-card"
                     onclick="location.href='${pageContext.request.contextPath}/vehicles?action=new'">
                    <i class="bi bi-car-front-fill text-success"></i>
                    <h5>Add Vehicle</h5>
                </div>
                <div class="card action-card"
                     onclick="location.href='${pageContext.request.contextPath}/drivers?action=new'">
                    <i class="bi bi-person-badge-fill text-warning"></i>
                    <h5>Add Driver</h5>
                </div>
                <div class="card action-card"
                     onclick="location.href='${pageContext.request.contextPath}/bookings?action=new'">
                    <i class="bi bi-calendar-plus text-info"></i>
                    <h5>New Booking</h5>
                </div>
                <div class="card action-card" onclick="location.href='${pageContext.request.contextPath}/bookings'">
                    <i class="bi bi-calendar-check text-danger"></i>
                    <h5>Manage Bookings</h5>
                </div>
            </div>
        </div>
    </div>


<%--
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
    </div>--%>

</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
    const baseUrl = '${pageContext.request.contextPath}';

    function navigate(path) {
        const url = `${baseUrl}/${path}?action=POST`;
        window.location.href = url;
    }

    function handleLogout() {
        navigate('logout');
    }

    document.addEventListener('DOMContentLoaded', function () {
        const currentPath = window.location.pathname;
        const navItems = {
            'app': 'nav-dashboard',
            'customers': 'nav-customers',
            'vehicles': 'nav-vehicles',
            'drivers': 'nav-drivers',
            'bookings': 'nav-bookings'
        };

        Object.values(navItems).forEach(id => {
            document.getElementById(id)?.classList.remove('active');
        });

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