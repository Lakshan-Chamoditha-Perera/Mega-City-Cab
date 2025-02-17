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
    </style>
</head>
<body class="py-5">
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

    <!-- Driver Management Section -->
    <div class="card p-4 mb-4">
        <div class="card-body">
            <div class="d-flex align-items-center justify-content-between mb-4">
                <h1 class="section-title">
                    <i class="bi bi-people-fill"></i>Manage Drivers
                </h1>
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
            <div class="table-container">
                <c:if test="${empty drivers}">
                    <div class="alert alert-info">
                        No drivers found. Add a new drivers to get started!
                    </div>
                </c:if>
                <c:if test="${not empty drivers}">
                    <table class="table table-hover driver-table mb-0">
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
</script>
</body>
</html>