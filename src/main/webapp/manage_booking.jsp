<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Bookings</title>
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
            --background-color: #f8f9fa;
            --hover-bg-color: rgba(13, 110, 253, 0.05);
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
        }

        .form-control:focus {
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.15);
        }

        .table-container {
            margin-top: 2rem;
            overflow-x: auto;
        }

        .booking-table {
            border-radius: 0.5rem;
            overflow: hidden;
        }

        .booking-table th {
            background-color: var(--primary-color);
            color: white;
            font-weight: 500;
            border: none;
        }

        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }

        .btn-primary:hover {
            background-color: #0b5ed7;
            border-color: #0a58ca;
        }

        .btn-warning {
            background-color: var(--warning-color);
            border-color: var(--warning-color);
        }

        .btn-danger {
            background-color: var(--danger-color);
            border-color: var(--danger-color);
        }

        .btn-secondary {
            background-color: var(--secondary-color);
            border-color: var(--secondary-color);
        }

        .btn-secondary:hover {
            background-color: #5c636a;
            border-color: #565e64;
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

        .search-form {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
            margin-bottom: 1.5rem;
        }

        .search-input {
            width: 250px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .form-floating {
            margin-bottom: 1rem;
        }

        .action-buttons {
            display: flex;
            gap: 0.5rem;
        }

        .alert {
            margin-bottom: 1.5rem;
        }

        .table-hover tbody tr:hover {
            background-color: var(--hover-bg-color);
        }

        .details-section {
            background-color: #fff;
            padding: 1rem;
            border-radius: 0.5rem;
            margin-bottom: 1rem;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }

        .details-section h5 {
            font-size: 18px;
            font-weight: 500;
            margin-bottom: 1rem;
        }

        .details-section p {
            margin: 0.5rem 0;
        }

        .cart-table {
            width: 100%;
            border-collapse: collapse;
        }

        .cart-table th, .cart-table td {
            padding: 0.75rem;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        .cart-table th {
            background-color: var(--primary-color);
            color: white;
        }

        .cart-table tr:hover {
            background-color: var(--hover-bg-color);
        }

        .cart-table .action-buttons {
            display: flex;
            gap: 0.5rem;
        }

        .activity-item {
            display: flex;
            align-items: center;
            padding: 1rem;
            border-bottom: 1px solid #e9ecef;
            transition: background-color 0.2s;
        }

        .activity-item:last-child {
            border-bottom: none;
        }

        .activity-item:hover {
            background-color: rgba(13, 110, 253, 0.05);
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
            background-color: rgba(13, 110, 253, 0.1);
            color: #0d6efd;
        }

        .activity-details {
            flex: 1;
        }

        .activity-details .d-flex {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .status-dropdown {
            width: 120px; /* Fixed width for consistency */
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
            border: 1px solid #ddd;
            border-radius: 0.375rem;
            transition: border-color 0.2s, box-shadow 0.2s;
        }

        .status-dropdown:focus {
            border-color: #0d6efd;
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.15);
        }

        /* Update button styling */
        .update-status-btn {
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
            border-radius: 0.375rem;
            transition: background-color 0.2s, color 0.2s;
        }

        .update-status-btn:hover {
            background-color: #0d6efd;
            color: white;
        }

        /* Badge styling */
        .badge {
            padding: 0.25rem 0.5rem;
            border-radius: 0.5rem;
            font-size: 0.75rem;
            font-weight: 500;
        }

        .badge.bg-warning {
            background-color: #ffc107;
            color: #000;
        }

        .badge.bg-success {
            background-color: #198754;
            color: #fff;
        }

        .activity-item {
            display: flex;
            align-items: center;
            padding: 1.5rem;
            border-bottom: 1px solid #e9ecef;
            border-radius: 0.5rem;
            margin-bottom: 1rem;
            background-color: #fff;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .activity-item:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            background-color: rgba(13, 110, 253, 0.03);
        }

        .activity-icon {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 1.5rem;
            font-size: 1.5rem;
            background-color: rgba(13, 110, 253, 0.1);
            color: #0d6efd;
            flex-shrink: 0;
        }

        .activity-details {
            flex: 1;
            min-width: 0; /* Prevents overflow */
        }

        .activity-details h6 {
            font-size: 1.25rem;
            font-weight: 600;
            color: #212529;
            margin-bottom: 0.5rem;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .activity-details .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 0.5rem;
            font-size: 0.9rem;
            color: #6c757d;
        }

        .activity-details .info-grid strong {
            color: #212529;
            font-weight: 500;
        }

        .activity-details .badge {
            padding: 0.35rem 0.75rem;
            font-size: 0.85rem;
            font-weight: 600;
            border-radius: 1rem;
            text-transform: capitalize;
        }

        .badge.bg-pending {
            background-color: #ffc107;
            color: #000;
        }

        .badge.bg-confirmed {
            background-color: #198754;
            color: #fff;
        }

        .badge.bg-canceled {
            background-color: #dc3545;
            color: #fff;
        }

        .action-buttons {
            display: flex;
            align-items: center;
            gap: 0.75rem;
            margin-left: 1rem;
        }

        .status-dropdown {
            width: 130px;
            padding: 0.4rem 0.5rem;
            font-size: 0.9rem;
            border-radius: 0.5rem;
        }

        .update-status-btn {
            padding: 0.4rem 0.75rem;
            font-size: 0.9rem;
            border-radius: 0.5rem;
        }

        .view-vehicles-btn {
            padding: 0.35rem 0.75rem;
            font-size: 0.9rem;
            border-radius: 0.5rem;
        }

        .modal-content {
            border-radius: 1rem;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        }

        .modal-header {
            background-color: #0d6efd;
            color: white;
            border-top-left-radius: 1rem;
            border-top-right-radius: 1rem;
        }

        .modal-body .list-group-item {
            border: none;
            padding: 1rem;
            border-bottom: 1px solid #e9ecef;
            font-size: 0.95rem;
        }

        .modal-body .list-group-item:last-child {
            border-bottom: none;
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

        .custom-help-icon {
            font-size: 1.2rem;
            color: #0d6efd;
            padding: 8px;
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .custom-help-icon:hover {
            transform: scale(1.1);
        }

        .custom-modal {
            border-radius: 12px;
            border: none;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .custom-modal .modal-header {
            background-color: #f8f9fa;
            border-bottom: 1px solid #e9ecef;
            border-radius: 12px 12px 0 0;
            padding: 1rem 1.5rem;
        }

        .custom-modal .modal-title {
            font-size: 1.25rem;
            font-weight: 600;
            color: #212529;
            display: flex;
            align-items: center;
        }

        .custom-modal .modal-title i {
            font-size: 1.5rem;
            color: #0d6efd;
            margin-right: 0.5rem;
        }

        .custom-modal .modal-body {
            padding: 1.5rem;
        }

        .custom-modal .modal-footer {
            background-color: #f8f9fa;
            border-top: 1px solid #e9ecef;
            border-radius: 0 0 12px 12px;
            padding: 1rem 1.5rem;
        }

        .custom-modal .btn-close {
            filter: invert(0.5);
        }

        .custom-modal .btn-close:hover {
            filter: invert(0.7);
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
        // Set active nav item based on current path
        document.addEventListener('DOMContentLoaded', function () {
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
<div class="container my-4">
    <!-- Success and Error Messages -->
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

    <!-- Booking Management Section -->
    <div class="card p-4 mb-4">
        <div class="card-body">
            <div class="d-flex align-items-center justify-content-between mb-4">
                <!-- Section Title -->
                <h1 class="section-title">
                    <i class="bi bi-calendar-check"></i> Manage Bookings
                </h1>

                <!-- Action Buttons -->
                <div class="d-flex gap-2">
                    <button id="show-view-button" class="btn btn-outline-primary">
                        <i class="bi bi-list-ul me-1"></i> All Bookings
                    </button>
                    <button id="show-form-button" class="btn btn-outline-success">
                        <i class="bi bi-plus-circle me-1"></i> Add New Booking
                    </button>

                    <!-- Help Button (Moved to right corner) -->
                    <i class="bi bi-question-circle custom-help-icon" data-bs-toggle="modal"
                       data-bs-target="#bookingGuidelinesModal"></i>
                </div>
            </div>

            <!-- Add/Edit Booking Form -->
            <form id="bookingForm" style="display: none;" action="${pageContext.request.contextPath}/bookings"
                  method="post">
                <input type="hidden" id="bookingId" name="bookingId">
                <input type="hidden" id="action" name="action" value="SAVE">

                <!-- Customer Details Section -->
                <div class="details-section">
                    <h5>Customer Details</h5>
                    <div class="row g-3">
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <select class="form-select" id="customerId" name="customerId" required
                                >
                                    <option value="">Select Customer</option>
                                    <c:forEach var="vehicle" items="${customers}">
                                        <option value="${vehicle.customerId}">${vehicle.firstName} ${vehicle.lastName}</option>
                                    </c:forEach>
                                </select>
                                <label for="customerId">Customer</label>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="mobileNo" placeholder="Mobile"
                                       readonly>
                                <label for="mobileNo">Mobile</label>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="customerEmail" placeholder="Email" readonly>
                                <label for="customerEmail">Email</label>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Booking Details Section -->
                <div class="details-section">
                    <h5>Booking Details</h5>
                    <div class="row g-3">
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="pickupLocation" name="pickupLocation"
                                       placeholder="Pickup Location" required>
                                <label for="pickupLocation">Pickup Location</label>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="destination" name="destination"
                                       placeholder="Destination" required>
                                <label for="destination">Destination</label>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="datetime-local" class="form-control" id="pickupTime" name="pickupTime"
                                       required>
                                <label for="pickupTime">Pickup Time</label>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <select class="form-select" id="status" name="status" required>
                                    <option value="pending">Pending</option>
                                    <option value="confirmed">Confirmed</option>
                                    <option value="canceled">Canceled</option>
                                </select>
                                <label for="status">Status</label>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="number" class="form-control" id="distance" name="distance"
                                       placeholder="Distance" required>
                                <label for="distance">Distance (km)</label>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="number" class="form-control" id="discount" name="discount"
                                       placeholder="Discount" value="0.00">
                                <label for="discount">Discount</label>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="number" class="form-control" id="tax" name="tax" placeholder="Tax"
                                       value="0.00">
                                <label for="tax">Tax</label>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Vehicle Details Section -->
                <div class="details-section">
                    <h5>Vehicle Details</h5>
                    <div class="row g-3">
                        <!-- Vehicle Selection -->
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <select class="form-select" id="vehicleId" name="vehicleId">
                                    <option value="">Select Vehicle</option>
                                    <c:forEach var="vehicle" items="${vehicles}">
                                        <option value="${vehicle.vehicleId}" data-price="${vehicle.pricePerKm}">
                                                ${vehicle.licensePlate} - ${vehicle.brand} ${vehicle.model}
                                        </option>
                                    </c:forEach>
                                </select>
                                <label for="vehicleId">Vehicle</label>
                            </div>
                        </div>

                        <!-- Vehicle Availability -->
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="vehicleAvailability"
                                       placeholder="Availability" readonly>
                                <label for="vehicleAvailability">Availability</label>
                            </div>
                        </div>

                        <!-- Vehicle Model -->
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="vehicleModel" placeholder="Model" readonly>
                                <label for="vehicleModel">Model</label>
                            </div>
                        </div>

                        <!-- Vehicle Brand -->
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="vehicleBrand" placeholder="Brand" readonly>
                                <label for="vehicleBrand">Brand</label>
                            </div>
                        </div>

                        <!-- Vehicle Color -->
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="vehicleColor" placeholder="Color" readonly>
                                <label for="vehicleColor">Color</label>
                            </div>
                        </div>

                        <!-- Driver Name -->
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="driverName" placeholder="Driver Name"
                                       readonly>
                                <label for="driverName">Driver Name</label>
                            </div>
                        </div>

                        <!-- Price per km -->
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="number" class="form-control" id="pricePerKm" placeholder="Price per km"
                                       readonly>
                                <label for="pricePerKm">Price per km</label>
                            </div>
                        </div>

                        <!-- Add to Cart Button -->
                        <div class="row mt-3">
                            <div class="col-12 text-end">
                                <button type="button" class="btn btn-primary" id="addToCartButton" disabled>
                                    <i class="bi bi-calendar-check me-2"></i>
                                    <span id="addToCartButtonText">Add to Cart</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Vehicle Cart Section -->
                <div class="details-section">
                    <h5>Vehicle Cart</h5>
                    <table class="cart-table">
                        <thead>
                        <tr>
                            <th>License Plate</th>
                            <th>Model</th>
                            <th>Price per km</th>
                            <th>Driver</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody id="cartTableBody">
                        <!-- Cart items will be dynamically added here -->
                        </tbody>
                    </table>
                </div>

                <!-- Total Price Section -->
                <div class="details-section">
                    <h5>Total Price</h5>
                    <div class="row g-3">
                        <div class="col-md-6 col-lg-4">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="totalPrice" placeholder="Total Price"
                                       readonly>
                                <label for="totalPrice">Total Price</label>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Form Buttons -->
                <div class="row mt-3">
                    <div class="col-12 text-end">
                        <button type="button" class="btn btn-secondary me-2" id="resetButton" aria-label="Reset Form">
                            <i class="bi bi-arrow-counterclockwise me-2"></i>Reset
                        </button>
                        <button type="submit" class="btn btn-primary" id="submitButton" aria-label="Submit Form">
                            <i class="bi bi-calendar-check me-2"></i>
                            <span id="submitButtonText">Confirm Booking</span>
                        </button>
                    </div>
                </div>
            </form>

            <div id="all-bookings-view">
                <div>
                    <div class="activity-card">
                        <c:forEach var="booking" items="${allBookings}">
                            <div class="activity-item">
                                <div class="activity-icon bg-primary">
                                    <i class="bi bi-calendar-check"></i>
                                </div>
                                <div class="activity-details">
                                    <h6>Booking #${booking.bookingId}</h6>
                                    <div class="info-grid">
                                        <div>
                                            <strong>Customer:</strong> ${booking.customerName}
                                        </div>
                                        <div class="mb-2">
                                            <strong class="text-secondary">📅 Booked Date:</strong>
                                            <span class="fw-bold">${booking.createdAt}</span>
                                        </div>
                                        <div>
                                            <strong class="text-secondary">🚗 Pickup Date:</strong>
                                            <span class="fw-bold text-success">${booking.pickupTime}</span>
                                        </div>

                                        <div>
                                            <strong>Status:</strong>
                                            <span class="badge bg-${booking.status == 'pending' ? 'pending' : booking.status == 'confirmed' ? 'confirmed' : 'canceled'}">
                                                    ${booking.status}
                                            </span>
                                        </div>
                                    </div>
                                    <div class="d-flex justify-content-between align-items-center mt-2">
                                        <button class="btn btn-sm btn-outline-primary view-vehicles-btn"
                                                data-bs-toggle="modal"
                                                data-bs-target="#vehicleModal${booking.bookingId}">
                                            <i class="bi bi-car-front me-1"></i> View Vehicles
                                        </button>
                                        <form action="${pageContext.request.contextPath}/bookings" method="post"
                                              class="action-buttons">
                                            <input type="hidden" name="bookingId" value="${booking.bookingId}">
                                            <input type="hidden" name="action" value="UPDATE">
                                            <select class="form-select status-dropdown" name="status">
                                                <option value="pending" ${booking.status == 'pending' ? 'selected' : ''}>
                                                    Pending
                                                </option>
                                                <option value="confirmed" ${booking.status == 'confirmed' ? 'selected' : ''}>
                                                    Confirmed
                                                </option>
                                                <option value="canceled" ${booking.status == 'canceled' ? 'selected' : ''}>
                                                    Canceled
                                                </option>
                                            </select>
                                            <button type="submit"
                                                    class="btn btn-sm btn-outline-success update-status-btn">
                                                <i class="bi bi-check-circle me-1"></i> Update
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <!-- Modal for Viewing Vehicles -->
                            <div class="modal fade" id="vehicleModal${booking.bookingId}" tabindex="-1"
                                 aria-labelledby="vehicleModalLabel${booking.bookingId}" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="vehicleModalLabel${booking.bookingId}">
                                                Vehicles for Booking #${booking.bookingId}
                                            </h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <c:if test="${not empty booking.vehicleList}">
                                                <ul class="list-group">
                                                    <c:forEach var="vehicle" items="${booking.vehicleList}">
                                                        <li class="list-group-item">
                                                            <strong>Vehicle ID:</strong> ${vehicle.vehicleId} <br>
                                                            <strong>Brand:</strong> ${vehicle.brand} <br>
                                                            <strong>Model:</strong> ${vehicle.model} <br>
                                                            <strong>License Plate:</strong> ${vehicle.licensePlate} <br>
                                                            <strong>Color:</strong> ${vehicle.color}
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </c:if>
                                            <c:if test="${empty booking.vehicleList}">
                                                <p class="text-muted">No vehicles assigned to this booking.</p>
                                            </c:if>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                Close
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<!-- Booking Management Modal -->
<div class="modal fade custom-modal" id="bookingGuidelinesModal" tabindex="-1"
     aria-labelledby="bookingGuidelinesModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h5 class="modal-title" id="bookingGuidelinesModalLabel">
                    <i class="bi bi-calendar-check me-2"></i>Booking Management Guidelines
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <!-- Modal Body -->
            <div class="modal-body">
                <h4>Booking Management</h4>
                <p>This section allows you to manage bookings for the Megacity Cab Service.</p>

                <div class="alert alert-info mb-4">
                    <i class="bi bi-info-circle me-2"></i>
                    Ensure all booking details are accurate and up-to-date.
                </div>

                <h5>1. Create a New Booking</h5>
                <ol>
                    <li>Click <strong>"New Booking"</strong> in Quick Actions or navigate to <strong>Bookings</strong>
                        in the navigation bar.
                    </li>
                    <li>Select a customer from the dropdown.</li>
                    <li>Enter booking details:
                        <ul>
                            <li><strong>Pickup Location</strong></li>
                            <li><strong>Destination</strong></li>
                            <li><strong>Pickup Time</strong></li>
                        </ul>
                    </li>
                    <li>The system will calculate distance and fare automatically.</li>
                    <li>Select vehicle(s) from the available fleet.</li>
                    <li>Click <strong>"Add to Cart"</strong> to include the vehicle in the booking.</li>
                    <li>Once all vehicles are added, view the total price.</li>
                    <li>Click <strong>"Confirm Booking"</strong> to finalize.</li>
                </ol>

                <h5>2. Manage Bookings</h5>
                <ol>
                    <li>Navigate to the <strong>Bookings</strong> section.</li>
                    <li>View all bookings in the list.</li>
                    <li>Update booking status (Pending, Confirmed, Canceled) as needed.</li>
                    <li>View vehicles assigned to each booking.</li>
                </ol>

                <h5>3. Important Notes</h5>
                <ul>
                    <li>A vehicle can only be in one active booking at a time.</li>
                    <li>A booking can include multiple vehicles.</li>
                    <li>Total fare is calculated based on distance and vehicle rates.</li>
                </ul>
            </div>

            <!-- Modal Footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const ITEMS_PER_PAGE = 10;
        const bookingsContainer = document.querySelector('.activity-card');
        const allBookingItems = Array.from(document.querySelectorAll('.activity-item'));
        const paginationContainer = document.createElement('div');
        paginationContainer.className = 'pagination-container d-flex justify-content-center mt-4';
        bookingsContainer.parentNode.insertBefore(paginationContainer, bookingsContainer.nextSibling);

        const filterContainer = document.createElement('div');
        filterContainer.className = 'filter-container mb-3';
        filterContainer.innerHTML = `
    <div class="row g-3">
      <div class="col-md-4">
        <div class="input-group">
          <span class="input-group-text">Search</span>
          <input type="text" id="search-input" class="form-control" placeholder="Customer name, booking ID...">
        </div>
      </div>
      <div class="col-md-3">
        <div class="input-group">
          <span class="input-group-text">Status</span>
          <select id="status-filter" class="form-select">
            <option value="">All Statuses</option>
            <option value="pending">Pending</option>
            <option value="confirmed">Confirmed</option>
            <option value="canceled">Canceled</option>
          </select>
        </div>
      </div>
      <div class="col-md-4">
        <div class="input-group">
          <span class="input-group-text">Date Range</span>
          <input type="date" id="date-from" class="form-control">
          <input type="date" id="date-to" class="form-control">
        </div>
      </div>
      <div class="col-md-1">
        <button id="reset-filters" class="btn btn-outline-secondary w-100">
          <i class="bi bi-x-circle"></i>
        </button>
      </div>
    </div>
  `;
        bookingsContainer.parentNode.insertBefore(filterContainer, bookingsContainer);

        // State
        let currentPage = 1;
        let filteredItems = [...allBookingItems];

        // Filter bookings based on search criteria
        function filterBookings() {
            const searchTerm = document.getElementById('search-input').value.toLowerCase();
            const statusFilter = document.getElementById('status-filter').value;
            const dateFrom = document.getElementById('date-from').value;
            const dateTo = document.getElementById('date-to').value;

            filteredItems = allBookingItems.filter(item => {
                const bookingText = item.textContent.toLowerCase();
                const statusMatch = statusFilter === '' ||
                    item.querySelector('.badge').textContent.trim().toLowerCase() === statusFilter;

                // Date filtering logic
                let dateMatch = true;
                if (dateFrom || dateTo) {
                    const pickupDateElement = item.querySelector('[class*="text-success"]');
                    if (pickupDateElement) {
                        const pickupDateStr = pickupDateElement.textContent.trim();
                        const pickupDate = new Date(pickupDateStr);

                        if (dateFrom && new Date(dateFrom) > pickupDate) {
                            dateMatch = false;
                        }

                        if (dateTo && new Date(dateTo) < pickupDate) {
                            dateMatch = false;
                        }
                    }
                }

                return bookingText.includes(searchTerm) && statusMatch && dateMatch;
            });

            currentPage = 1;
            renderBookings();
            renderPagination();
        }

        // Render bookings for current page
        function renderBookings() {
            // Hide all bookings first
            allBookingItems.forEach(item => {
                item.style.display = 'none';
            });

            // Calculate start and end index for current page
            const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
            const endIndex = Math.min(startIndex + ITEMS_PER_PAGE, filteredItems.length);

            // Show only the bookings for current page
            for (let i = startIndex; i < endIndex; i++) {
                filteredItems[i].style.display = 'flex';
            }

            // Show empty state if no results
            if (filteredItems.length === 0) {
                const emptyState = document.createElement('div');
                emptyState.className = 'empty-state text-center py-4';
                emptyState.innerHTML = `
        <i class="bi bi-search text-muted" style="font-size: 2rem;"></i>
        <p class="mt-2 mb-0">No bookings match your filters</p>
      `;

                // Remove any existing empty state
                const existingEmptyState = bookingsContainer.querySelector('.empty-state');
                if (existingEmptyState) {
                    existingEmptyState.remove();
                }

                bookingsContainer.appendChild(emptyState);
            } else {
                // Remove empty state if exists
                const existingEmptyState = bookingsContainer.querySelector('.empty-state');
                if (existingEmptyState) {
                    existingEmptyState.remove();
                }
            }
        }

        // Render pagination controls
        function renderPagination() {
            const totalPages = Math.ceil(filteredItems.length / ITEMS_PER_PAGE);

            // Clear existing pagination
            paginationContainer.innerHTML = '';

            if (totalPages <= 1) {
                return;
            }

            const paginationNav = document.createElement('nav');
            paginationNav.setAttribute('aria-label', 'Bookings pagination');

            const paginationList = document.createElement('ul');
            paginationList.className = 'pagination';

            // Previous button
            const prevItem = document.createElement('li');
            prevItem.className = `page-item ${currentPage == 1 ? 'disabled' : ''}`;
            prevItem.innerHTML = `<a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>`;
            prevItem.addEventListener('click', (e) => {
                e.preventDefault();
                if (currentPage > 1) {
                    currentPage--;
                    renderBookings();
                    renderPagination();
                }
            });
            paginationList.appendChild(prevItem);

            // Page numbers
            const maxButtons = 5;
            let startPage = Math.max(1, currentPage - Math.floor(maxButtons / 2));
            let endPage = Math.min(totalPages, startPage + maxButtons - 1);

            // Adjust start if we're near the end
            if (endPage - startPage + 1 < maxButtons) {
                startPage = Math.max(1, endPage - maxButtons + 1);
            }

            for (let i = startPage; i <= endPage; i++) {
                const pageItem = document.createElement('li');
                pageItem.className = `page-item ${i == currentPage ? 'active' : ''}`;
                pageItem.innerHTML = `<a class="page-link" href="#">${i}</a>`;
                pageItem.addEventListener('click', (e) => {
                    e.preventDefault();
                    currentPage = i;
                    renderBookings();
                    renderPagination();
                    // Scroll to top of bookings container
                    bookingsContainer.scrollIntoView({behavior: 'smooth'});
                });
                paginationList.appendChild(pageItem);
            }

            // Next button
            const nextItem = document.createElement('li');
            nextItem.className = `page-item ${currentPage == totalPages ? 'disabled' : ''}`;
            nextItem.innerHTML = `<a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a>`;
            nextItem.addEventListener('click', (e) => {
                e.preventDefault();
                if (currentPage < totalPages) {
                    currentPage++;
                    renderBookings();
                    renderPagination();
                }
            });
            paginationList.appendChild(nextItem);

            paginationNav.appendChild(paginationList);
            paginationContainer.appendChild(paginationNav);

            // Add pagination info
            const paginationInfo = document.createElement('div');
            paginationInfo.className = 'pagination-info ms-3 d-flex align-items-center';
            paginationInfo.innerHTML = `Showing ${filteredItems.length > 0 ? (currentPage - 1) * ITEMS_PER_PAGE + 1 : 0}-${Math.min(currentPage * ITEMS_PER_PAGE, filteredItems.length)} of ${filteredItems.length} bookings`;
            paginationContainer.appendChild(paginationInfo);
        }

        // Add event listeners
        document.getElementById('search-input').addEventListener('input', filterBookings);
        document.getElementById('status-filter').addEventListener('change', filterBookings);
        document.getElementById('date-from').addEventListener('change', filterBookings);
        document.getElementById('date-to').addEventListener('change', filterBookings);
        document.getElementById('reset-filters').addEventListener('click', () => {
            document.getElementById('search-input').value = '';
            document.getElementById('status-filter').value = '';
            document.getElementById('date-from').value = '';
            document.getElementById('date-to').value = '';
            filteredItems = [...allBookingItems];
            currentPage = 1;
            renderBookings();
            renderPagination();
        });

        // Initialize
        renderBookings();
        renderPagination();

        // Add CSS styles
        const style = document.createElement('style');
        style.textContent = `
    .activity-item {
      display: flex;
      margin-bottom: 1rem;
      padding: 1rem;
      border: 1px solid #e9ecef;
      border-radius: 0.5rem;
      background-color: #fff;
    }
    .pagination-container {
      margin-bottom: 2rem;
    }
    .filter-container {
      background-color: #f8f9fa;
      padding: 1rem;
      border-radius: 0.5rem;
      margin-bottom: 1.5rem;
    }
    .empty-state {
      width: 100%;
      padding: 2rem;
      border: 1px dashed #dee2e6;
      border-radius: 0.5rem;
      color: #6c757d;
    }
  `;
        document.head.appendChild(style);
    });

    // Function to toggle visibility of the "View All Bookings" section and the booking form
    function toggleView(viewId, formId) {
        const viewSection = document.getElementById(viewId);
        const formSection = document.getElementById(formId);

        if (viewSection && formSection) {
            if (viewSection.style.display === "none") {
                // Show the view section and hide the form
                viewSection.style.display = "block";
                formSection.style.display = "none";
            } else {
                // Hide the view section and show the form
                viewSection.style.display = "none";
                formSection.style.display = "block";
            }
        }
    }

    // Function to initialize event listeners for the buttons
    function initializeButtons() {
        const showViewButton = document.getElementById("show-view-button");
        const showFormButton = document.getElementById("show-form-button");

        if (showViewButton && showFormButton) {
            // Add click event listeners to the buttons
            showViewButton.addEventListener("click", () => {
                toggleView("all-bookings-view", "bookingForm");
            });

            showFormButton.addEventListener("click", () => {
                toggleView("all-bookings-view", "bookingForm");
            });
        }
    }

    document.addEventListener("DOMContentLoaded", initializeButtons);
</script>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        // Data from server-side
        const customersData = {
            <c:forEach var="customer" items="${customers}">
            "${customer.customerId}": {
                mobile: "${customer.mobileNo}",
                email: "${customer.email}"
            }<c:if test="${not customer.equals(customers[customers.size()-1])}">, </c:if>
            </c:forEach>
        };

        const vehiclesData = {
            <c:forEach var="vehicle" items="${vehicles}">
            "${vehicle.vehicleId}": {
                licensePlate: "${vehicle.licensePlate}",
                model: "${vehicle.model}",
                brand: "${vehicle.brand}",
                color: "${vehicle.color}",
                pricePerKm: ${vehicle.pricePerKm},
                availability: ${vehicle.availability},
                driverId: "${vehicle.driverId}",
                driverName: "${vehicle.driverName}"
            }<c:if test="${not vehicle.equals(vehicles[vehicles.size()-1])}">, </c:if>
            </c:forEach>
        };

        const cart = [];

        const elements = {
            customerId: document.getElementById("customerId"),
            mobileNo: document.getElementById("mobileNo"),
            customerEmail: document.getElementById("customerEmail"),

            vehicleId: document.getElementById("vehicleId"),
            vehicleModel: document.getElementById("vehicleModel"),
            vehicleBrand: document.getElementById("vehicleBrand"),
            vehicleColor: document.getElementById("vehicleColor"),
            pricePerKm: document.getElementById("pricePerKm"),
            vehicleAvailability: document.getElementById("vehicleAvailability"),
            driverName: document.getElementById("driverName"),
            addToCartButton: document.getElementById("addToCartButton"),
            distance: document.getElementById("distance"),
            totalPrice: document.getElementById("totalPrice"),
            cartTableBody: document.getElementById("cartTableBody"),

            form: document.getElementById("bookingForm"),
            resetButton: document.getElementById("resetButton"),
            submitButton: document.getElementById("submitButton"),
            submitButtonText: document.getElementById("submitButtonText")
        };

        let cartInput = document.getElementById("cartData") || createHiddenInput("cartData");
        let vehicleIdsInput = document.getElementById("vehicleIds") || createHiddenInput("vehicleIds");

        function createHiddenInput(name) {
            const input = document.createElement("input");
            input.type = "hidden";
            input.id = name;
            input.name = name;
            elements.form.appendChild(input);
            return input;
        }

        function createTableCell(text) {
            const cell = document.createElement("td");
            cell.textContent = text || "";
            return cell;
        }

        function updateCustomerDetails() {
            const customerId = elements.customerId.value;
            const customer = customersData[customerId];

            elements.mobileNo.value = customer ? customer.mobile : "";
            elements.customerEmail.value = customer ? customer.email : "";
        }

        function updateVehicleDetails() {
            const vehicleId = elements.vehicleId.value;
            const vehicle = vehiclesData[vehicleId];

            if (vehicle) {
                elements.vehicleModel.value = vehicle.model;
                elements.vehicleBrand.value = vehicle.brand;
                elements.vehicleColor.value = vehicle.color;
                elements.pricePerKm.value = vehicle.pricePerKm.toFixed(2);
                elements.vehicleAvailability.value = vehicle.availability ? "Available" : "Not Available";
                elements.driverName.value = (!vehicle.driverId || vehicle.driverId === "0" || !vehicle.driverName)
                    ? "No Driver"
                    : vehicle.driverName;
                elements.addToCartButton.disabled = !vehicle.availability;
            } else {
                clearVehicleDetails();
            }
        }

        function clearVehicleDetails() {
            elements.vehicleModel.value = "";
            elements.vehicleBrand.value = "";
            elements.vehicleColor.value = "";
            elements.pricePerKm.value = "";
            elements.vehicleAvailability.value = "";
            elements.driverName.value = "";
            elements.addToCartButton.disabled = true;
        }

        function addToCart() {
            try {
                const vehicleId = elements.vehicleId.value;
                const vehicle = vehiclesData[vehicleId];
                const distance = parseFloat(elements.distance.value) || 0;

                if (!distance || distance <= 0) {
                    alert("Please enter a valid distance greater than 0");
                    return;
                }
                if (!vehicle) {
                    alert("Please select a valid vehicle.");
                    return;
                }
                if (!vehicle.availability) {
                    alert("This vehicle is not available.");
                    return;
                }
                if (cart.some(item => item.vehicleId === vehicleId)) {
                    alert("This vehicle is already in the cart.");
                    return;
                }

                cart.push({
                    vehicleId,
                    licensePlate: vehicle.licensePlate,
                    model: vehicle.model,
                    brand: vehicle.brand,
                    pricePerKm: vehicle.pricePerKm,
                    driverName: vehicle.driverName || "No Driver",
                    driverId: vehicle.driverId,
                    distance
                });

                updateCartTable();
                updateTotalPrice();
                updateVehicleIds();
            } catch (error) {
                console.error("Error adding to cart:", error);
                alert("An error occurred while adding to cart.");
            }
        }

        function updateTotalPrice() {
            let distance = parseFloat(elements.distance.value) || 0;
            const total = cart.reduce((sum, item) => sum + (item.pricePerKm * distance), 0);
            elements.totalPrice.value = total.toFixed(2);
        }

        function updateCartTable() {
            elements.cartTableBody.innerHTML = "";

            cart.forEach((item, index) => {
                const row = document.createElement("tr");
                row.appendChild(createTableCell(item.licensePlate));
                row.appendChild(createTableCell(item.model));
                row.appendChild(createTableCell(item.pricePerKm.toFixed(2)));
                row.appendChild(createTableCell(item.driverName));

                const actionsCell = document.createElement("td");
                const removeButton = document.createElement("button");
                removeButton.textContent = "Remove";
                removeButton.className = "btn btn-danger btn-sm";
                removeButton.addEventListener("click", () => {
                    cart.splice(index, 1);
                    updateCartTable();
                    updateTotalPrice();
                    updateVehicleIds();
                });
                actionsCell.appendChild(removeButton);
                row.appendChild(actionsCell);

                elements.cartTableBody.appendChild(row);
            });
        }

        function updateVehicleIds() {
            const vehicleIds = cart.map(item => item.vehicleId);
            vehicleIdsInput.value = JSON.stringify(vehicleIds);
        }

        function validateSubmission() {
            if (cart.length === 0) {
                alert("Cannot submit booking: Cart is empty. Please add at least one vehicle.");
                return false;
            }
            if (!elements.customerId.value) {
                alert("Please select a customer before submitting.");
                return false;
            }

            for (const item of cart) {
                if (!item.vehicleId || !item.licensePlate || !item.pricePerKm || !item.distance) {
                    alert("Invalid cart item: Missing required information.");
                    return false;
                }
                if (item.distance <= 0 || isNaN(item.distance)) {
                    alert("Invalid distance: Distance must be greater than 0 for all items.");
                    return false;
                }
                if (item.pricePerKm <= 0 || isNaN(item.pricePerKm)) {
                    alert("Invalid price: Price per KM must be greater than 0 for all items.");
                    return false;
                }
            }
            return true;
        }

        function handleSubmit(event) {
            elements.submitButton.disabled = true;
            elements.submitButtonText.textContent = "Processing...";

            if (!validateSubmission()) {
                event.preventDefault();
                resetButtonState();
                return;
            }

            cartInput.value = JSON.stringify(cart);
            updateVehicleIds(); // Ensure vehicleIds is up-to-date before submission
        }

        function resetButtonState() {
            elements.submitButton.disabled = false;
            elements.submitButtonText.textContent = "Confirm Booking";
        }

        function resetForm() {
            cart.length = 0;
            elements.cartTableBody.innerHTML = "";
            elements.form.reset();
            cartInput.value = "";
            vehicleIdsInput.value = ""; // Clear vehicleIds
            resetButtonState();
            updateCustomerDetails();
            updateVehicleDetails();
        }

        // Event Listeners
        elements.customerId.addEventListener("change", updateCustomerDetails);
        elements.vehicleId.addEventListener("change", updateVehicleDetails);
        elements.addToCartButton.addEventListener("click", addToCart);
        elements.form.addEventListener("submit", handleSubmit);
        elements.resetButton.addEventListener("click", () => {
            if (confirm("Are you sure you want to reset the form? This will clear your cart.")) {
                resetForm();
            }
        });

        updateCustomerDetails();
        updateVehicleDetails();
        console.table({customers: customersData, vehicles: vehiclesData});
    });
</script>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const statusForms = document.querySelectorAll('.activity-item form');
        statusForms.forEach(form => {
            form.addEventListener('submit', function (e) {
                const submitButton = this.querySelector('.update-status-btn');
                submitButton.disabled = true;
                submitButton.innerHTML = '<i class="bi bi-hourglass-split me-1"></i> Updating...';

                setTimeout(() => {
                    submitButton.disabled = false;
                    submitButton.innerHTML = '<i class="bi bi-check-circle me-1"></i> Update';
                }, 2000);
            });
        });
    });
</script>
</body>
</html>