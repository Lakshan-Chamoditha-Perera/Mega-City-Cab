<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Drivers</title>
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

        .driver-table {
            border-radius: 0.5rem;
            overflow: hidden;
        }

        .driver-table th {
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



        .pagination-container {
            margin-top: 1.5rem;
            padding-top: 1rem;
            border-top: 1px solid rgba(0, 0, 0, 0.1);
        }

        .pagination-info {
            color: var(--secondary-color);
            font-size: 0.9rem;
        }

        .pagination .page-link {
            color: var(--primary-color);
            background-color: #fff;
            border-color: #dee2e6;
        }

        .pagination .page-item.active .page-link {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
            color: white;
        }

        .pagination .page-item.disabled .page-link {
            color: #6c757d;
            pointer-events: none;
            background-color: #fff;
            border-color: #dee2e6;
        }

        .pagination .page-link:focus {
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
        }

        .pagination .page-link:hover {
            background-color: #e9ecef;
            border-color: #dee2e6;
        }

        /* Items per page selector styling */
        .items-per-page-container {
            margin-bottom: 1rem;
        }

        .items-per-page-container label {
            font-size: 0.9rem;
            color: var(--secondary-color);
        }

        .items-per-page-container .form-select {
            border-color: #dee2e6;
        }

        .items-per-page-container .form-select:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
        }
    </style>
</head>
<body class="">

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

    <!-- Driver Management Section -->
    <div class="card p-4 mb-4">
        <div class="card-body">
            <div class="d-flex align-items-center justify-content-between mb-4">
                <h1 class="section-title">
                    <i class="bi bi-people-fill me-2"></i>Manage Drivers
                </h1>
                <i class="bi bi-question-circle custom-help-icon" data-bs-toggle="modal"
                   data-bs-target="#driverGuidelinesModal"></i>
            </div>

            <!-- Add/Edit Driver Form -->
            <form id="driverForm" action="${pageContext.request.contextPath}/drivers" method="post">
                <input type="hidden" id="driverId" name="driverId">
                <input type="hidden" id="action" name="action" value="SAVE">
                <div class="row g-3">
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="firstName" name="firstName"
                                   placeholder="First Name" required>
                            <label for="firstName">First Name</label>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="lastName" name="lastName"
                                   placeholder="Last Name" required>
                            <label for="lastName">Last Name</label>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="licenseNumber" name="licenseNumber"
                                   placeholder="License Number" required>
                            <label for="licenseNumber">License Number</label>
                        </div>
                    </div>

                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="tel" class="form-control" id="mobileNo" name="mobileNo" placeholder="Mobile No"
                                   required>
                            <label for="mobileNo">Mobile No</label>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email"
                                   required>
                            <label for="email">Email</label>
                        </div>
                    </div>
                    <div class="col-12 text-end">
                        <!-- Reset Button -->
                        <button type="button" class="btn btn-secondary me-2" id="resetButton" aria-label="Reset Form">
                            <i class="bi bi-arrow-counterclockwise me-2"></i>Reset
                        </button>
                        <!-- Submit Button -->
                        <button type="submit" class="btn btn-primary" id="submitButton" aria-label="Submit Form">
                            <i class="bi bi-person-plus-fill me-2"></i>
                            <span id="submitButtonText">Add Driver</span>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- Driver List Section -->
    <div class="card p-4">
        <div class="card-body">
            <h2 class="section-title">
                <i class="bi bi-table me-2"></i>
                Driver List
            </h2>
            <div class="d-flex justify-content-end mb-3">
                <button class="btn btn-secondary me-2" onclick="printDriversList()">
                    <i class="bi bi-printer"></i> Print
                </button>
                <button class="btn btn-success" onclick="exportToCSV()">
                    <i class="bi bi-file-earmark-spreadsheet"></i> Export as CSV
                </button>
            </div>
            <div class="table-container">
                <c:if test="${empty drivers}">
                    <div class="alert alert-info">
                        No drivers found. Add a new drivers to get started!
                    </div>
                </c:if>
                <c:if test="${not empty drivers}">
                    <table id="driverTable" class="table table-hover driver-table mb-0">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Licence Number</th>
                            <th>Mobile No</th>
                            <th>Email</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="driver" items="${drivers}">
                            <tr>
                                <td>${driver.driverId}</td>
                                <td>${driver.firstName != null ? driver.firstName : '-'}</td>
                                <td>${driver.lastName != null ? driver.lastName : '-'}</td>
                                <td>${driver.licenseNumber != null ? driver.licenseNumber : '-'}</td>
                                <td>${driver.mobileNo != null ? driver.mobileNo : '-'}</td>
                                <td>${driver.email != null ? driver.email : '-'}</td>
                                <td>
                                    <div class="action-buttons">
                                        <!-- Edit button -->
                                        <button type="button" class="btn btn-sm btn-warning edit-button"
                                                data-driver-id="${driver.driverId}"
                                                data-first-name="${driver.firstName}"
                                                data-last-name="${driver.lastName}"
                                                data-license-number="${driver.licenseNumber}"
                                                data-mobile-no="${driver.mobileNo}"
                                                data-email="${driver.email}"
                                                aria-label="Edit Driver">
                                            Edit
                                        </button>
                                        <!-- Delete Form -->
                                        <form action="${pageContext.request.contextPath}/drivers" method="post"
                                              onsubmit="return confirmDelete();" style="display:inline;">
                                            <input type="hidden" name="action" value="DELETE">
                                            <input type="hidden" name="driverId" value="${driver.driverId}">
                                            <button type="submit" class="btn btn-sm btn-danger"
                                                    aria-label="Delete Driver">Delete
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
<!-- Driver Management Modal -->
<!-- Driver Management Modal -->
<div class="modal fade custom-modal" id="driverGuidelinesModal" tabindex="-1" aria-labelledby="driverGuidelinesModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h5 class="modal-title" id="driverGuidelinesModalLabel">
                    <i class="bi bi-person-badge me-2"></i>Driver Management Guidelines
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <!-- Modal Body -->
            <div class="modal-body">
                <h4>Driver Management</h4>
                <p>This section allows you to manage driver information for the Megacity Cab Service.</p>

                <div class="alert alert-info mb-4">
                    <i class="bi bi-info-circle me-2"></i>
                    Ensure all driver details are accurate and up-to-date.
                </div>

                <h5>1. Add a New Driver</h5>
                <ol>
                    <li>Click <strong>"Add Driver"</strong> in Quick Actions or navigate to <strong>Drivers</strong> in the navigation bar.</li>
                    <li>Fill in all required fields:
                        <ul>
                            <li><strong>First Name</strong></li>
                            <li><strong>Last Name</strong></li>
                            <li><strong>License Number</strong></li>
                            <li><strong>Mobile No</strong></li>
                            <li><strong>Email</strong></li>
                        </ul>
                    </li>
                    <li>Click <strong>"Save"</strong> to register the driver.</li>
                </ol>

                <h5>2. Update Driver Information</h5>
                <ol>
                    <li>Locate the driver in the table below the form.</li>
                    <li>Click the <strong>"Update"</strong> button in the Actions column.</li>
                    <li>The form will be populated with the driver's data.</li>
                    <li>Make necessary changes and click <strong>"Update"</strong>.</li>
                    <li>The table will refresh automatically with updated information.</li>
                </ol>
            </div>

            <!-- Modal Footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<!-- Bootstrap JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
    // Function to confirm deletion
    function confirmDelete() {
        return confirm('Are you sure you want to delete this driver?');
    }

    // Edit Button Functionality
    document.addEventListener('click', function (event) {
        if (event.target.classList.contains('edit-button')) {
            const button = event.target;
            // Fill the form with driver data
            document.getElementById('driverId').value = button.dataset.driverId;
            document.getElementById('firstName').value = button.dataset.firstName;
            document.getElementById('lastName').value = button.dataset.lastName;
            document.getElementById('licenseNumber').value = button.dataset.licenseNumber;
            document.getElementById('mobileNo').value = button.dataset.mobileNo;
            document.getElementById('email').value = button.dataset.email;

            // Change the form action to "UPDATE"
            document.getElementById('action').value = 'UPDATE';

            // Change the button text to "Update"
            document.getElementById('submitButtonText').textContent = 'Update Driver';
        }
    });

    // Reset Button Functionality
    document.getElementById('resetButton').addEventListener('click', function () {
        // Clear the form
        document.getElementById('driverForm').reset();

        // Reset the hidden fields
        document.getElementById('driverId').value = '';
        document.getElementById('action').value = 'SAVE';

        // Change the button text back to "Add Driver"
        document.getElementById('submitButtonText').textContent = 'Add Driver';
    });


    document.addEventListener('DOMContentLoaded', function () {
        const itemsPerPage = 10;
        let currentPage = 1;

        const tableBody = document.querySelector('.driver-table tbody');
        if (!tableBody) return;

        const tableRows = Array.from(tableBody.querySelectorAll('tr'));
        const totalPages = Math.ceil(tableRows.length / itemsPerPage);

        function displayTableRows() {
            tableRows.forEach(row => {
                row.style.display = 'none';
            });

            const startIndex = (currentPage - 1) * itemsPerPage;
            const endIndex = Math.min(startIndex + itemsPerPage, tableRows.length);

            for (let i = startIndex; i < endIndex; i++) {
                tableRows[i].style.display = '';
            }

            document.getElementById('pagination-info').textContent =
                `Showing ${startIndex + 1} to ${endIndex} of ${tableRows.length} customers`;

            document.querySelectorAll('.page-item').forEach((item, index) => {
                if (index === 0) {
                    item.classList.toggle('disabled', currentPage === 1);
                } else if (index === document.querySelectorAll('.page-item').length - 1) { // Next button
                    item.classList.toggle('disabled', currentPage === totalPages);
                } else { // Page number buttons
                    const pageNum = parseInt(item.querySelector('.page-link').textContent);
                    item.classList.toggle('active', pageNum === currentPage);
                }
            });
        }

        function createPagination() {
            const paginationContainer = document.createElement('div');
            paginationContainer.className = 'pagination-container d-flex justify-content-between align-items-center mt-3';

            const paginationInfo = document.createElement('div');
            paginationInfo.id = 'pagination-info';
            paginationInfo.className = 'pagination-info';

            const paginationNav = document.createElement('nav');
            paginationNav.setAttribute('aria-label', 'Driver table navigation');

            const paginationList = document.createElement('ul');
            paginationList.className = 'pagination pagination-sm mb-0';

            const prevItem = document.createElement('li');
            prevItem.className = 'page-item disabled';
            const prevLink = document.createElement('a');
            prevLink.className = 'page-link';
            prevLink.href = '#';
            prevLink.setAttribute('aria-label', 'Previous');
            prevLink.innerHTML = '<span aria-hidden="true">&laquo;</span>';
            prevItem.appendChild(prevLink);
            paginationList.appendChild(prevItem);

            for (let i = 1; i <= totalPages; i++) {
                const pageItem = document.createElement('li');
                pageItem.className = 'page-item' + (i === 1 ? ' active' : '');
                const pageLink = document.createElement('a');
                pageLink.className = 'page-link';
                pageLink.href = '#';
                pageLink.textContent = i;
                pageItem.appendChild(pageLink);
                paginationList.appendChild(pageItem);
            }

            const nextItem = document.createElement('li');
            nextItem.className = 'page-item' + (totalPages === 1 ? ' disabled' : '');
            const nextLink = document.createElement('a');
            nextLink.className = 'page-link';
            nextLink.href = '#';
            nextLink.setAttribute('aria-label', 'Next');
            nextLink.innerHTML = '<span aria-hidden="true">&raquo;</span>';
            nextItem.appendChild(nextLink);
            paginationList.appendChild(nextItem);

            paginationNav.appendChild(paginationList);
            paginationContainer.appendChild(paginationInfo);
            paginationContainer.appendChild(paginationNav);

            const tableContainer = document.querySelector('.table-container');
            tableContainer.appendChild(paginationContainer);

            prevLink.addEventListener('click', function (e) {
                e.preventDefault();
                if (currentPage > 1) {
                    currentPage--;
                    displayTableRows();
                }
            });

            nextLink.addEventListener('click', function (e) {
                e.preventDefault();
                if (currentPage < totalPages) {
                    currentPage++;
                    displayTableRows();
                }
            });
            document.querySelectorAll('.page-item:not(:first-child):not(:last-child) .page-link').forEach(link => {
                link.addEventListener('click', function (e) {
                    e.preventDefault();
                    currentPage = parseInt(this.textContent);
                    displayTableRows();
                });
            });
        }

        if (tableRows.length > 0) {
            createPagination();
            displayTableRows();
        }
        document.getElementById('driverForm').addEventListener('submit', function () {
            currentPage = 1;
        });
    });

    function printDriversList() {
        const printContents = document.getElementById('driverTable').outerHTML;
        const originalContents = document.body.innerHTML;

        document.body.innerHTML = printContents;
        window.print();
        document.body.innerHTML = originalContents;
        window.location.reload();
    }

    function exportToCSV() {
        const table = document.getElementById('driverTable');
        const rows = table.querySelectorAll('tr');
        let csvContent = '';

        rows.forEach(row => {
            const rowData = [];
            row.querySelectorAll('th, td').forEach(cell => {
                if (!cell.querySelector('button')) {
                    rowData.push(cell.innerText);
                }
            });
            csvContent += rowData.join(',') + '\n';
        });

        const blob = new Blob([csvContent], {type: 'text/csv;charset=utf-8;'});
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = 'drivers.csv';
        link.click();
    }
</script>
</body>
</html>