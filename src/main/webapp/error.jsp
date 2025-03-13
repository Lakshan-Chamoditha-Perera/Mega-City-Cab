<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Megacity Cab Service</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.11.1/font/bootstrap-icons.min.css" rel="stylesheet">
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
            display: flex;
            flex-direction: column;
        }

        .error-container {
            flex-grow: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 2rem;
        }

        .error-card {
            background: white;
            border: none;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            border-radius: 1rem;
            max-width: 800px;
            width: 100%;
            padding: 3rem;
            text-align: center;
        }

        .error-icon {
            font-size: 5rem;
            margin-bottom: 1.5rem;
        }

        .error-code {
            font-weight: bold;
            letter-spacing: 0.1em;
            margin-bottom: 1.5rem;
        }

        .return-text {
            margin-top: 1.5rem;
            color: var(--secondary-color);
        }

        .action-buttons {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
            margin-top: 2rem;
        }

        .details-container {
            background-color: #f8f9fa;
            border-radius: 0.5rem;
            padding: 1.5rem;
            margin-top: 2rem;
            text-align: left;
        }

        .progress {
            height: 5px;
        }
    </style>
</head>
<body>
<%-- Include navbar --%>
<%--<jsp:include page="navbar.jsp" />--%>

<div class="error-container">
    <div class="error-card">
        <%
            String errorTitle = "Unexpected Error";
            String errorMessage = "An unexpected error occurred while processing your request.";
            String errorCode = "500";
            String errorId = "ERR-" + System.currentTimeMillis();
            String errorIcon = "bi-exclamation-triangle";
            String errorColor = "danger";
            String errorSuggestion = "Our team has been notified and is working to fix the issue.";

            if (request.getAttribute("errorTitle") != null) {
                errorTitle = (String) request.getAttribute("errorTitle");
            }
            if (request.getAttribute("errorMessage") != null) {
                errorMessage = (String) request.getAttribute("errorMessage");
            }
            if (request.getAttribute("errorCode") != null) {
                errorCode = (String) request.getAttribute("errorCode");
            }
            if (request.getAttribute("errorId") != null) {
                errorId = (String) request.getAttribute("errorId");
            }

            if ("401".equals(errorCode)) {
                errorIcon = "bi-shield-lock";
                errorColor = "warning";
                errorSuggestion = "Please log in to continue.";
            } else if ("403".equals(errorCode)) {
                errorIcon = "bi-shield-exclamation";
                errorColor = "warning";
                errorSuggestion = "You don't have permission to access this resource.";
            } else if ("404".equals(errorCode)) {
                errorIcon = "bi-question-circle";
                errorColor = "info";
                errorSuggestion = "The page you are looking for might have been removed or is temporarily unavailable.";
            }

            int countdown = 10;
            if (request.getAttribute("countdown") != null) {
                try {
                    countdown = Integer.parseInt(request.getAttribute("countdown").toString());
                } catch (NumberFormatException e) {
                }
            }

            String returnUrl = request.getContextPath() + "/home";
            if (request.getAttribute("returnUrl") != null) {
                returnUrl = (String) request.getAttribute("returnUrl");
            }
        %>

        <div class="error-icon text-<%= errorColor %>">
            <i class="bi <%= errorIcon %>"></i>
        </div>

        <h1 class="display-4 fw-bold mb-3"><%= errorTitle %></h1>

        <div class="error-code">
            <span class="badge bg-secondary">Error <%= errorCode %></span>
            <span class="badge bg-dark ms-2"><%= errorId %></span>
        </div>

        <p class="lead mb-3"><%= errorMessage %></p>
        <p class="text-muted"><%= errorSuggestion %></p>

        <div class="action-buttons">
            <a href="<%= returnUrl %>" class="btn btn-primary">
                <i class="bi bi-house-door me-2"></i>Return to Dashboard
            </a>

            <button onclick="window.location.reload()" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-clockwise me-2"></i>Refresh Page
            </button>

            <a href="${pageContext.request.contextPath}/help" class="btn btn-outline-info">
                <i class="bi bi-question-circle me-2"></i>Get Help
            </a>
        </div>

        <div class="return-text">
            <small>Automatically returning to dashboard in <span id="countdown"><%= countdown %></span> seconds</small>
            <div class="progress mt-2">
                <div id="countdown-progress" class="progress-bar bg-primary" role="progressbar" style="width: 100%"></div>
            </div>
        </div>

        <%-- Technical Details (Visible to admins or in dev mode) --%>
        <% if (exception != null && (session != null && session.getAttribute("isAdmin") != null && (Boolean)session.getAttribute("isAdmin"))) { %>
        <div class="details-container mt-4">
            <button class="btn btn-sm btn-outline-secondary mb-3" type="button" data-bs-toggle="collapse"
                    data-bs-target="#errorDetails" aria-expanded="false" aria-controls="errorDetails">
                <i class="bi bi-code-slash me-2"></i>Technical Details
            </button>

            <div class="collapse" id="errorDetails">
                <h5 class="mb-3">Exception Details</h5>
                <div class="mb-3">
                    <strong>Type:</strong> <%= exception.getClass().getName() %>
                </div>
                <div class="mb-3">
                    <strong>Message:</strong> <%= exception.getMessage() %>
                </div>
                <div>
                    <strong>Stack Trace:</strong>
                    <pre class="bg-dark text-light p-3 mt-2" style="border-radius: 0.5rem; overflow: auto; max-height: 300px;"><%
                        java.io.StringWriter sw = new java.io.StringWriter();
                        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
                        exception.printStackTrace(pw);
                    %></pre>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
    // Countdown logic
    let secondsLeft = <%= countdown %>;
    const countdownElement = document.getElementById('countdown');
    const progressBar = document.getElementById('countdown-progress');
    const totalSeconds = <%= countdown %>;

    function updateCountdown() {
        secondsLeft--;
        countdownElement.textContent = secondsLeft;

        // Update progress bar
        const progressWidth = (secondsLeft / totalSeconds) * 100;
        progressBar.style.width = progressWidth + '%';

        if (secondsLeft <= 0) {
            window.location.href = '<%= returnUrl %>';
        } else {
            setTimeout(updateCountdown, 1000);
        }
    }
    setTimeout(updateCountdown, 1000);
    console.error('Error <%= errorCode %>: <%= errorMessage %> (<%= errorId %>)');
</script>
</body>
</html>