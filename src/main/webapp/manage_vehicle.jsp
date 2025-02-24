<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Vehicles</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
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
        }

        .form-control:focus {
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.15);
        }

        .table-container {
            margin-top: 2rem;
            overflow-x: auto;
        }

        .vehicle-table {
            border-radius: 0.5rem;
            overflow: hidden;
        }

        .vehicle-table th {
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
                    <form action="${pageContext.request.contextPath}/app" method="get">
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

    <!-- Vehicle Management Section -->
    <div class="card p-4 mb-4">
        <div class="card-body">
            <div class="d-flex align-items-center justify-content-between mb-4">
                <h1 class="section-title">
                    <i class="bi bi-car-front-fill"></i>Manage Vehicles
                </h1>
            </div>

            <!-- Add/Edit Vehicle Form -->
            <form id="vehicleForm" action="${pageContext.request.contextPath}/vehicles" method="post">
                <input type="hidden" id="vehicleId" name="vehicleId">
                <input type="hidden" id="action" name="action" value="SAVE">
                <div class="row g-3">
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="licensePlate" name="licensePlate"
                                   placeholder="License Plate" required>
                            <label for="licensePlate">License Plate</label>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="model" name="model" placeholder="Model"
                                   required>
                            <label for="model">Model</label>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="brand" name="brand" placeholder="Brand"
                                   required>
                            <label for="brand">Brand</label>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="number" class="form-control" id="passengerCount" name="passengerCount"
                                   placeholder="Passenger Count" required>
                            <label for="passengerCount">Passenger Count</label>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="color" name="color" placeholder="Color"
                                   required>
                            <label for="color">Color</label>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <select class="form-control" id="availability" name="availability" required>
                                <option value="true">Available</option>
                                <option value="false">Not Available</option>
                            </select>
                            <label for="availability">Availability</label>
                        </div>
                    </div>
<%--                    <div class="col-md-6 col-lg-4">--%>
<%--                        <div class="form-floating">--%>
<%--                            <input type="date" class="form-control" id="createdAt" name="createdAt" required>--%>
<%--                            <label for="createdAt">Created At</label>--%>
<%--                        </div>--%>
<%--                    </div>--%>

                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <select class="form-select" id="driverId" name="driverId">
                                <option value="0">No Driver</option> <!-- Add this option -->
                                <c:if test="${not empty drivers}">
                                    <c:forEach var="driver" items="${drivers}">
                                        <option value="${driver.driverId}">
                                                ${driver.firstName} ${driver.lastName}
                                        </option>
                                    </c:forEach>
                                </c:if>
                            </select>
                            <label for="driverId">Driver</label>
                        </div>
                    </div>
                    <div class="col-12 text-end">
                        <!-- Reset Button -->
                        <button type="button" class="btn btn-secondary me-2" id="resetButton" aria-label="Reset Form">
                            <i class="bi bi-arrow-counterclockwise me-2"></i>Reset
                        </button>
                        <!-- Submit Button -->
                        <button type="submit" class="btn btn-primary" id="submitButton" aria-label="Submit Form">
                            <i class="bi bi-car-front-fill me-2"></i>
                            <span id="submitButtonText">Add Vehicle</span>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- Vehicle List Section -->
    <div class="card p-4">
        <div class="card-body">
            <h2 class="section-title">
                <i class="bi bi-table me-2"></i>
                Vehicle List
            </h2>
            <div class="table-container">
                <c:if test="${empty vehicles}">
                    <div class="alert alert-info">
                        No vehicles found. Add a new vehicle to get started!
                    </div>
                </c:if>
                <c:if test="${not empty vehicles}">
                    <table class="table table-hover vehicle-table mb-0">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>License Plate</th>
                            <th>Model</th>
                            <th>Brand</th>
                            <th>Passenger Count</th>
                            <th>Color</th>
                            <th>Availability</th>
                            <th>Driver</th> <!-- Updated column header -->
                            <th>Created Date</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="vehicle" items="${vehicles}">
                            <tr>
                                <td>${vehicle.vehicleId}</td>
                                <td>${vehicle.licensePlate}</td>
                                <td>${vehicle.model}</td>
                                <td>${vehicle.brand}</td>
                                <td>${vehicle.passengerCount}</td>
                                <td>${vehicle.color}</td>
                                <td>${vehicle.availability ? 'Available' : 'Not Available'}</td>
                                <td>
                                    <!-- Display "No Driver" if driverId is 0 -->
                                    <c:choose>
                                        <c:when test="${vehicle.driverId == 0 || vehicle.driverId == null}">
                                            No Driver
                                        </c:when>
                                        <c:otherwise>
                                            ${vehicle.driverId}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    ${vehicle.createdAt}
                                </td>
                                <td>
                                    <div class="action-buttons">
                                        <!-- Edit button -->
                                        <button type="button" class="btn btn-sm btn-warning edit-button"
                                                data-vehicle-id="${vehicle.vehicleId}"
                                                data-license-plate="${vehicle.licensePlate}"
                                                data-model="${vehicle.model}"
                                                data-brand="${vehicle.brand}"
                                                data-passenger-count="${vehicle.passengerCount}"
                                                data-color="${vehicle.color}"
                                                data-is-available="${vehicle.availability}"
                                                data-driver-id="${vehicle.driverId}"
<%--                                                data-created-at="${vehicle.createdAt}"--%>
                                                aria-label="Edit Vehicle">
                                            Edit
                                        </button>
                                        <!-- Delete Form -->
                                        <form action="${pageContext.request.contextPath}/vehicles" method="post"
                                              onsubmit="return confirmDelete();" style="display:inline;">
                                            <input type="hidden" name="action" value="DELETE">
                                            <input type="hidden" name="vehicleId" value="${vehicle.vehicleId}">
                                            <button type="submit" class="btn btn-sm btn-danger"
                                                    aria-label="Delete Vehicle">Delete
                                            </button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
    // Function to confirm deletion
    function confirmDelete() {
        return confirm('Are you sure you want to delete this vehicle?');
    }

    // Edit Button Functionality
    document.addEventListener('click', function (event) {
        if (event.target.classList.contains('edit-button')) {
            const button = event.target;
            // Fill the form with vehicle data
            document.getElementById('vehicleId').value = button.dataset.vehicleId;
            document.getElementById('licensePlate').value = button.dataset.licensePlate;
            document.getElementById('model').value = button.dataset.model;
            document.getElementById('brand').value = button.dataset.brand;
            document.getElementById('passengerCount').value = button.dataset.passengerCount;
            document.getElementById('color').value = button.dataset.color;
            document.getElementById('availability').value = button.dataset.availability;
            document.getElementById('driverId').value = button.dataset.driverId;
            // document.getElementById('createdAt').value = button.dataset.createdAt;
            // Change the form action to "UPDATE"
            document.getElementById('action').value = 'UPDATE';

            // Change the button text to "Update"
            document.getElementById('submitButtonText').textContent = 'Update Vehicle';
        }
    });

    // Reset Button Functionality
    document.getElementById('resetButton').addEventListener('click', function () {
        // Clear the form
        document.getElementById('vehicleForm').reset();

        // Reset the hidden fields
        document.getElementById('vehicleId').value = '';
        document.getElementById('action').value = 'SAVE';

        // Change the button text back to "Add Vehicle"
        document.getElementById('submitButtonText').textContent = 'Add Vehicle';
    });
</script>
</body>
</html>