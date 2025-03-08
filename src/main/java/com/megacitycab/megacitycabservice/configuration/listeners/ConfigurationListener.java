package com.megacitycab.megacitycabservice.configuration.listeners;

import jakarta.servlet.ServletContextListener;

/**
 * The {@code ConfigurationListener} interface extends {@link ServletContextListener}
 * to provide a contract for handling application-specific configuration events during
 * the deployment and un-deployment lifecycle of a JavaEE web application.
 * <p>
 * Implementing classes can define custom behavior to:
 * - Initialize resources or configurations when the application starts.
 * - Release resources or perform cleanup when the application shuts down.
 * <p>
 * This interface serves as a marker or base interface to categorize
 * configuration listeners within the application.
 */
public interface ConfigurationListener extends ServletContextListener {
}
