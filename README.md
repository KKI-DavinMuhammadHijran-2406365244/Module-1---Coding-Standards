# Module 2
### Reflection 4.2
1. When I was looking at my coverage score on the Jacoco index.html, I noticed that my score was low. So I made a dozen of test to make sure my coverage score was better. The strategy that I use was making the test according to the Jacoco index.html. In there I make tests for each method that hasn't been tested.
2. Yes I think so !, because I make sure that my CI/CD implementation will always check, rebuild, test for every push or pull attempt. For now I believe that my CI/CD is enough. But maybe in the next few weeks, I would have new problems and I should make a better CI/CD implementation.


-------------------------------------------------------------

# Module 1
### Reflection 1

In implementing the Edit Product and Delete Product features using Spring Boot, several clean code principles were applied throughout the project. The code follows a clear separation of concerns by dividing responsibilities across controllers, services, repositories, and models, which improves readability and maintainability. Meaningful class and method names were used to reflect their purposes, such as ProductController, ProductService, and repository interfaces for data access. Reusable logic was placed in the service layer to avoid duplication, and consistent formatting and naming conventions were maintained across the codebase to enhance clarity.

From a secure coding perspective, the application avoids exposing internal implementation details by encapsulating business logic within the service layer and interacting with data only through repositories. Input handling is managed through controller methods instead of direct data manipulation, reducing the risk of unintended behavior. One improvement that can be made is adding stronger validation for user inputs (such as preventing empty product names or invalid quantities) and providing proper error handling or feedback to users when invalid data is submitted. Adding validation annotations and exception handling would further improve robustness, security, and user experience while keeping the code clean and maintainable.

### Reflection 2

1. After writing the unit tests, I felt more confident about the correctness and stability of the implemented features. Unit tests help verify that each method behaves as expected in both positive and negative scenarios, making future changes safer and easier. There is no fixed number of unit tests required for a class; instead, tests should be written to cover all important logic paths, edge cases, and possible failure scenarios. To ensure that unit tests are sufficient, developers can use code coverage metrics, which show how much of the source code is executed during testing. Code coverage tools help identify untested areas that may contain hidden bugs or unverified logic.

However, achieving 100% code coverage does not guarantee that the code is free of bugs or errors. Code coverage only measures which lines or branches are executed, not whether the logic is correct or whether all real-world scenarios are handled properly. A test can execute a line of code without validating its correctness. Therefore, unit tests should be well-designed, include meaningful assertions, and be complemented by other types of testing such as integration and functional tests. Good test quality, not just high coverage, is essential for building reliable and maintainable software.

2. Creating another functional test suite with the same setup procedures and instance variables as existing tests can lead to code duplication, which negatively impacts code cleanliness and maintainability. Repeating the same WebDriver setup, base URL configuration, and test initialization logic across multiple functional test classes violates the DRY (Don’t Repeat Yourself) principle. This duplication makes the code harder to maintain because any change to the setup logic (such as modifying the base URL or browser configuration) would need to be updated in multiple places, increasing the risk of inconsistencies and errors.

To improve code cleanliness, common setup logic should be extracted into a shared abstract base class or a reusable test utility that all functional test suites can extend or use. This approach reduces duplication and improves readability by allowing each test class to focus only on test behavior rather than infrastructure code. Additionally, using helper methods for common user interactions (such as creating a product or navigating to the product list) can further improve clarity and reusability. These improvements enhance maintainability, reduce technical debt, and ensure that adding new functional tests does not degrade overall code quality.
