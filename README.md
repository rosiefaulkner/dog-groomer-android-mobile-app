# GroomerApp

GroomerApp is an Android application designed to assist users in calculating and placing orders for pet grooming services. With a user-friendly interface, the app allows users to input their pet's weight, select additional grooming services, and receive a dynamically calculated total price. Key features include input validation, debounced input handling for smooth performance, and visual cues for enhanced usability.

## Features

- **Weight Input with Validation**: Ensures the user provides a valid weight for accurate price calculations.
- **Debounced Input Handling**: Delays calculations until the user has finished typing to improve responsiveness and prevent redundant calculations.
- **Customizable Add-Ons**: Allows selection of additional services (e.g., flea bath, nail trim, massage) with each add-on affecting the total price.
- **Real-Time Price Calculation**: Automatically updates and displays the total cost based on the pet's weight and selected services.
- **Order and Reset Functions**: Users can place an order once all required information is entered or reset all fields to start fresh.
- **Interactive UI**: Highlights input fields and adjusts button states according to validation, providing a seamless user experience.

## Technologies Used

- **Android SDK**
- **Java**
- **TextWatcher**: For handling text input and validation.
- **Handler** and `Runnable`: To implement debounced actions for smoother input processing.
- **Spannable**: Used for dynamic text styling and highlighting.

## Installation

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Build and run the project on an Android emulator or physical device.

## Usage

1. Enter your pet's weight in the weight input field.
2. Choose any desired add-on services.
3. Tap **Calculate** to view the total cost.
4. Tap **Order** to place the order, or **Reset** to clear all inputs and start over.

---

Customize this description as needed based on any additional features or specific user interaction details.
