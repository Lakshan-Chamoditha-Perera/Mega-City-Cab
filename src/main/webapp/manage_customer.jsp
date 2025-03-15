<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Customers</title>
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

        .customer-table {
            border-radius: 0.5rem;
            overflow: hidden;
        }

        .customer-table th {
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

        /* Pagination styling */

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

    <!-- Customer Management Section -->
    <div class="card p-4 my-4">
        <div class="card-body">
            <div class="d-flex align-items-center justify-content-between mb-4">
                <h1 class="section-title">
                    <i class="bi bi-people-fill me-2"></i>Manage Customers
                </h1>
                <i class="bi bi-question-circle custom-help-icon" data-bs-toggle="modal"
                   data-bs-target="#customerGuidelinesModal"></i>
            </div>

            <!-- Add/Edit Customer Form -->
            <form id="customerForm" action="${pageContext.request.contextPath}/customers" method="post">
                <input type="hidden" id="customerId" name="customerId">
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
                            <input type="text" class="form-control" id="address" name="address" placeholder="Address"
                                   required>
                            <label for="address">Address</label>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="nic" name="nic" placeholder="NIC" required>
                            <label for="nic">NIC</label>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-4">
                        <div class="form-floating">
                            <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" required>
                            <label for="dateOfBirth">Date of Birth</label>
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
                            <span id="submitButtonText">Add Customer</span>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="card p-4">
        <div class="card-body">
            <h2 class="section-title">
                <i class="bi bi-table me-2"></i>
                Customer List
            </h2>
            <div class="d-flex justify-content-end mb-3">

                <button class="btn btn-secondary me-2" onclick="printCustomerList()">
                    <i class="bi bi-printer"></i> Print
                </button>
                <button class="btn btn-success" onclick="exportToCSV()">
                    <i class="bi bi-file-earmark-spreadsheet"></i> Export as CSV
                </button>
            </div>
            <div class="filter-container mb-3">
                <div class="row g-3">
                    <div class="col-md-4">
                        <div class="input-group">
                            <span class="input-group-text">Search</span>
                            <input type="text" id="search-input" class="form-control"
                                   placeholder="Name, mobile, email...">
                        </div>
                    </div>
                </div>
            </div>
            <div class="table-container">

                <c:if test="${empty customers}">
                    <div class="alert alert-info">
                        No customers found. Add a new customer to get started!
                    </div>
                </c:if>
                <c:if test="${not empty customers}">
                    <table class="table table-hover customer-table mb-0" id="customerTable">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Address</th>
                            <th>NIC</th>
                            <th>Date of Birth</th>
                            <th>Mobile No</th>
                            <th>Email</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="vehicle" items="${customers}">
                            <tr>
                                <td>${vehicle.customerId}</td>
                                <td>${vehicle.firstName != null ? vehicle.firstName : '-'}</td>
                                <td>${vehicle.lastName != null ? vehicle.lastName : '-'}</td>
                                <td>${vehicle.address != null ? vehicle.address : '-'}</td>
                                <td>${vehicle.nic != null ? vehicle.nic : '-'}</td>
                                <td>${vehicle.dateOfBirth != null ? vehicle.dateOfBirth : '-'}</td>
                                <td>${vehicle.mobileNo != null ? vehicle.mobileNo : '-'}</td>
                                <td>${vehicle.email != null ? vehicle.email : '-'}</td>
                                <td>
                                    <div class="action-buttons">
                                        <!-- Edit button -->
                                        <button type="button" class="btn btn-sm btn-warning edit-button"
                                                data-customer-id="${vehicle.customerId}"
                                                data-first-name="${vehicle.firstName}"
                                                data-last-name="${vehicle.lastName}"
                                                data-address="${vehicle.address}"
                                                data-nic="${vehicle.nic}"
                                                data-date-of-birth="${vehicle.dateOfBirth}"
                                                data-mobile-no="${vehicle.mobileNo}"
                                                data-email="${vehicle.email}"
                                                aria-label="Edit Customer">
                                            Edit
                                        </button>
                                        <!-- Delete Form -->
                                        <form action="${pageContext.request.contextPath}/customers" method="post"
                                              onsubmit="return confirmDelete();" style="display:inline;">
                                            <input type="hidden" name="action" value="DELETE">
                                            <input type="hidden" name="customerId" value="${vehicle.customerId}">
                                            <button type="submit" class="btn btn-sm btn-danger"
                                                    aria-label="Delete Customer">Delete
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

<div class="modal fade custom-modal" id="customerGuidelinesModal" tabindex="-1"
     aria-labelledby="customerGuidelinesModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="customerGuidelinesModalLabel">
                    <i class="bi bi-people me-2"></i>Customer Management Guidelines
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <h4>Customer Management</h4>
                <p>This section allows you to manage customer information for the Megacity Cab Service.</p>

                <div class="alert alert-info mb-4">
                    <i class="bi bi-info-circle me-2"></i>
                    Ensure all customer details are accurate and up-to-date.
                </div>

                <h5>1. Register a New Customer</h5>
                <ol>
                    <li>Click <strong>"Add Customer"</strong> in Quick Actions or navigate to <strong>Customers</strong>
                        in the navigation bar.
                    </li>
                    <li>Fill in all required fields:
                        <ul>
                            <li><strong>First Name</strong></li>
                            <li><strong>Last Name</strong></li>
                            <li><strong>Address</strong></li>
                            <li><strong>NIC</strong></li>
                            <li><strong>Date of Birth</strong></li>
                            <li><strong>Mobile No</strong></li>
                            <li><strong>Email</strong></li>
                        </ul>
                    </li>
                    <li>Click <strong>"Save"</strong> to register the customer.</li>
                    <li>A success message will appear at the top of the form when successful.</li>
                </ol>

                <h5>2. Update Customer Information</h5>
                <ol>
                    <li>Locate the customer in the table below the form.</li>
                    <li>Click the <strong>"Update"</strong> button in the Actions column.</li>
                    <li>The form will be populated with the customer's data.</li>
                    <li>Make necessary changes and click <strong>"Update"</strong>.</li>
                    <li>The table will refresh automatically with updated information.</li>
                </ol>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
    function confirmDelete() {
        return confirm('Are you sure you want to delete this customer?');
    }

    document.addEventListener('click', function (event) {
        if (event.target.classList.contains('edit-button')) {
            const button = event.target;
            // Fill the form with customer data
            document.getElementById('customerId').value = button.dataset.customerId;
            document.getElementById('firstName').value = button.dataset.firstName;
            document.getElementById('lastName').value = button.dataset.lastName;
            document.getElementById('address').value = button.dataset.address;
            document.getElementById('nic').value = button.dataset.nic;
            document.getElementById('dateOfBirth').value = button.dataset.dateOfBirth;
            document.getElementById('mobileNo').value = button.dataset.mobileNo;
            document.getElementById('email').value = button.dataset.email;

            // Change the form action to "UPDATE"
            document.getElementById('action').value = 'UPDATE';

            // Change the button text to "Update"
            document.getElementById('submitButtonText').textContent = 'Update Customer';
        }
    });

    document.getElementById('resetButton').addEventListener('click', function () {
        // Clear the form
        document.getElementById('customerForm').reset();

        // Reset the hidden fields
        document.getElementById('customerId').value = '';
        document.getElementById('action').value = 'SAVE';

        // Change the button text back to "Add Customer"
        document.getElementById('submitButtonText').textContent = 'Add Customer';
    });

    document.addEventListener('DOMContentLoaded', function () {
        // Pagination configuration
        const itemsPerPage = 10;
        let currentPage = 1;

        const tableBody = document.querySelector('.customer-table tbody');
        if (!tableBody) return; // Exit if table doesn't exist

        const tableRows = Array.from(tableBody.querySelectorAll('tr'));
        const totalPages = Math.ceil(tableRows.length / itemsPerPage);

        // Function to display rows for current page
        function displayTableRows() {
            // Hide all rows first
            tableRows.forEach(row => {
                row.style.display = 'none';
            });

            // Calculate which rows to show
            const startIndex = (currentPage - 1) * itemsPerPage;
            const endIndex = Math.min(startIndex + itemsPerPage, tableRows.length);

            // Show only rows for current page
            for (let i = startIndex; i < endIndex; i++) {
                tableRows[i].style.display = '';
            }

            // Update pagination info text
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
            paginationNav.setAttribute('aria-label', 'Customer table navigation');

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
        document.getElementById('customerForm').addEventListener('submit', function () {
            currentPage = 1;
        });
    });
    document.addEventListener('DOMContentLoaded', function () {
        const ITEMS_PER_PAGE = 10;
        let currentPage = 1;
        let filteredRows = [];

        const tableBody = document.querySelector('.customer-table tbody');
        if (!tableBody) return;
        const tableRows = Array.from(tableBody.querySelectorAll('tr'));
        filteredRows = [...tableRows];

        function filterRows() {
            const searchTerm = document.getElementById('search-input').value.toLowerCase().trim();

            filteredRows = tableRows.filter(row => {
                const fullName = `${row.cells[1].textContent.toLowerCase()} ${row.cells[2].textContent.toLowerCase()}`;
                const mobileNo = row.cells[6].textContent.toLowerCase();
                const email = row.cells[7].textContent.toLowerCase();

                return (
                    fullName.includes(searchTerm) ||
                    mobileNo.includes(searchTerm) ||
                    email.includes(searchTerm)
                );

            });

            currentPage = 1; // Reset to first page
            displayTableRows();
            renderPagination();
        }

        // Display rows for current page
        function displayTableRows() {
            tableRows.forEach(row => {
                row.style.display = 'none';
            });

            const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
            const endIndex = Math.min(startIndex + ITEMS_PER_PAGE, filteredRows.length);

            for (let i = startIndex; i < endIndex; i++) {
                filteredRows[i].style.display = '';
            }

            // Show empty state if no results
            const tableContainer = document.querySelector('.table-container');
            let emptyState = tableContainer.querySelector('.empty-state');
            if (filteredRows.length === 0) {
                if (!emptyState) {
                    emptyState = document.createElement('div');
                    emptyState.className = 'empty-state text-center py-4';
                    emptyState.innerHTML = `
                    <i class="bi bi-search text-muted" style="font-size: 2rem;"></i>
                    <p class="mt-2 mb-0">No customers match your filters</p>
                `;
                    tableContainer.appendChild(emptyState);
                }
            } else if (emptyState) {
                emptyState.remove();
            }
        }

        // Render pagination controls
        function renderPagination() {
            let paginationContainer = document.querySelector('.pagination-container');
            if (!paginationContainer) {
                paginationContainer = document.createElement('div');
                paginationContainer.className = 'pagination-container d-flex justify-content-between align-items-center mt-3';
                const tableContainer = document.querySelector('.table-container');
                tableContainer.appendChild(paginationContainer);
            } else {
                paginationContainer.innerHTML = '';
            }

            const totalPages = Math.ceil(filteredRows.length / ITEMS_PER_PAGE);
            if (totalPages <= 1 && filteredRows.length > 0) {
                document.getElementById('pagination-info').textContent =
                    `Showing 1 to ${filteredRows.length} of ${filteredRows.length} customers`;
                return;
            }

            const paginationNav = document.createElement('nav');
            paginationNav.setAttribute('aria-label', 'Customer table navigation');

            const paginationList = document.createElement('ul');
            paginationList.className = 'pagination pagination-sm mb-0';

            // Previous button
            const prevItem = document.createElement('li');
            prevItem.className = `page-item ${currentPage == 1 ? 'disabled' : ''}`;
            prevItem.innerHTML = `<a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">«</span></a>`;
            prevItem.addEventListener('click', (e) => {
                e.preventDefault();
                if (currentPage > 1) {
                    currentPage--;
                    displayTableRows();
                    renderPagination();
                }
            });
            paginationList.appendChild(prevItem);

            // Page numbers (limited to 5 buttons)
            const maxButtons = 5;
            let startPage = Math.max(1, currentPage - Math.floor(maxButtons / 2));
            let endPage = Math.min(totalPages, startPage + maxButtons - 1);
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
                    displayTableRows();
                    renderPagination();
                });
                paginationList.appendChild(pageItem);
            }

            // Next button
            const nextItem = document.createElement('li');
            nextItem.className = `page-item ${currentPage == totalPages ? 'disabled' : ''}`;
            nextItem.innerHTML = `<a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">»</span></a>`;
            nextItem.addEventListener('click', (e) => {
                e.preventDefault();
                if (currentPage < totalPages) {
                    currentPage++;
                    displayTableRows();
                    renderPagination();
                }
            });
            paginationList.appendChild(nextItem);

            const paginationInfo = document.createElement('div');
            paginationInfo.id = 'pagination-info';
            paginationInfo.className = 'pagination-info';
            const startIndex = (currentPage - 1) * ITEMS_PER_PAGE + 1;
            const endIndex = Math.min(currentPage * ITEMS_PER_PAGE, filteredRows.length);
            paginationInfo.textContent =
                `Showing ${filteredRows.length > 0 ? startIndex : 0} to ${endIndex} of ${filteredRows.length} customers`;

            paginationNav.appendChild(paginationList);
            paginationContainer.appendChild(paginationInfo);
            paginationContainer.appendChild(paginationNav);
        }

        // Event listeners for filters
        document.getElementById('search-input').addEventListener('input', filterRows);
        document.getElementById('status-filter').addEventListener('change', filterRows);
        document.getElementById('date-from').addEventListener('change', filterRows);
        document.getElementById('date-to').addEventListener('change', filterRows);


        // Initial setup
        if (tableRows.length > 0) {
            renderPagination();
            displayTableRows();
        }

        // Add custom styles
        const style = document.createElement('style');
        style.textContent = `
        .filter-container {
            background-color: #f8f9fa;
            padding: 1rem;
            border-radius: 0.5rem;
            margin-bottom: 1.5rem;
        }
        .pagination-container {
            margin-bottom: 2rem;
        }
        .empty-state {
            width: 100%;
            padding: 2rem;
            border: 1px dashed #dee2e6;
            border-radius: 0.5rem;
            color: #6c757d;
        }
        .customer-table {
            background-color: #fff;
            border-radius: 0.5rem;
        }
    `;
        document.head.appendChild(style);

        // Reset pagination on form submission
        document.getElementById('customerForm')?.addEventListener('submit', function () {
            currentPage = 1;
        });
    });

    function printCustomerList() {
        const printContents = document.getElementById('customerTable').outerHTML;
        const originalContents = document.body.innerHTML;

        document.body.innerHTML = printContents;
        window.print();
        document.body.innerHTML = originalContents;
        window.location.reload(); // Reload to restore the page
    }

    // Function to export the customer list as CSV
    function exportToCSV() {
        const table = document.getElementById('customerTable');
        const rows = table.querySelectorAll('tr');
        let csvContent = '';

        rows.forEach(row => {
            const rowData = [];
            row.querySelectorAll('th, td').forEach(cell => {
                if (!cell.querySelector('button')) { // Exclude action buttons
                    rowData.push(cell.innerText);
                }
            });
            csvContent += rowData.join(',') + '\n';
        });

        const blob = new Blob([csvContent], {type: 'text/csv;charset=utf-8;'});
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = 'customers.csv';
        link.click();
    }
</script>
</body>
</html>