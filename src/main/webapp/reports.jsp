<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Revenue Reports</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
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
<body>

<nav class="navbar navbar-expand-lg sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="#" onclick="navigate('app')">
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
                    <form action="${pageContext.request.contextPath}/reports" method="get">
                        <button class="nav-link active" type="submit" id="nav-reports">
                            <i class="bi bi-bar-chart-line"></i>
                            Revenue Reports
                        </button>
                    </form>
                </li>
            </ul>
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
</nav>

<div class="container my-4">
    <c:if test="${param.success != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>Success!</strong> ${param.success}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${param.error != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error!</strong> ${param.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="card p-4">
        <div class="card-body">
            <h1 class="section-title">
                <i class="bi bi-bar-chart-line"></i> Revenue Reports
            </h1>

            <form action="${pageContext.request.contextPath}/reports" method="get">
                <div class="row g-3">
                    <div class="col-md-4">
                        <label for="reportType" class="form-label">Report Type</label>
                        <select class="form-select" id="reportType" name="reportType" required>
                            <option value="WEEKLY">Weekly</option>
                            <option value="MONTHLY">Monthly</option>
                            <option value="YEARLY">Yearly</option>
                        </select>
                    </div>
                    <div class="col-12 text-end">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-filter"></i> Generate Report
                        </button>
                    </div>
                </div>
            </form>

            <!-- Report Data Table -->
            <div class="table-container mt-4">
                <c:if test="${empty reportData}">
                    <div class="alert alert-info">
                        No data found for the selected filters.
                    </div>
                </c:if>
                <c:if test="${not empty reportData}">
                    <table class="table table-hover report-table">
                        <thead>
                        <tr>
                            <th>Period</th>
                            <th>Total Revenue</th>
                            <th>Discounts</th>
                            <th>Taxes</th>
                            <th>Net Revenue</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="row" items="${reportData}">
                            <tr>
                                <td>${row.period}</td>
                                <td>${row.totalRevenue}</td>
                                <td>${row.totalDiscounts}</td>
                                <td>${row.totalTaxes}</td>
                                <td>${row.netRevenue}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const currentPath = window.location.pathname;
        const navItems = {
            'app': 'nav-dashboard',
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
</body>
</html>