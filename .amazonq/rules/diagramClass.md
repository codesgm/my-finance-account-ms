classDiagram
%% ===== ENTITIES =====
class User {
-Long id
-String name
-String email
-LocalDateTime createdAt
-LocalDateTime lastAccess
-List~Revenue~ revenues
-List~Expense~ expenses
-List~Category~ categories
+User()
+User(String name, String email)
+getId() Long
+getName() String
+getEmail() String
+getCreatedAt() LocalDateTime
+getLastAccess() LocalDateTime
+setName(String name) void
+setEmail(String email) void
+setLastAccess(LocalDateTime lastAccess) void
+getRevenues() List~Revenue~
+getExpenses() List~Expense~
+getCategories() List~Category~
+addRevenue(Revenue revenue) void
+removeRevenue(Revenue revenue) void
+addExpense(Expense expense) void
+removeExpense(Expense expense) void
+addCategory(Category category) void
+removeCategory(Category category) void
+updateLastAccess() void
+onCreate() void
+equals(Object o) boolean
+hashCode() int
+toString() String
}

class Category {
-Long id
-String name
-TransactionType type
-String icon
-String color
-User user
-List~Revenue~ revenues
-List~Expense~ expenses
+Category()
+Category(String name, TransactionType type, User user)
+getId() Long
+getName() String
+getType() TransactionType
+getIcon() String
+getColor() String
+getUser() User
+setName(String name) void
+setIcon(String icon) void
+setColor(String color) void
+setUser(User user) void
+getRevenues() List~Revenue~
+getExpenses() List~Expense~
+addRevenue(Revenue revenue) void
+removeRevenue(Revenue revenue) void
+addExpense(Expense expense) void
+removeExpense(Expense expense) void
+isRevenue() boolean
+isExpense() boolean
+onCreate() void
+equals(Object o) boolean
+hashCode() int
+toString() String
}

class Revenue {
-Long id
-String description
-BigDecimal amount
-LocalDate date
-LocalDateTime createdAt
-User user
-Category category
+Revenue()
+Revenue(String description, BigDecimal amount, LocalDate date, User user, Category category)
+getId() Long
+getDescription() String
+getAmount() BigDecimal
+getDate() LocalDate
+getCreatedAt() LocalDateTime
+getUser() User
+getCategory() Category
+setDescription(String description) void
+setAmount(BigDecimal amount) void
+setDate(LocalDate date) void
+setUser(User user) void
+setCategory(Category category) void
+onCreate() void
+equals(Object o) boolean
+hashCode() int
+toString() String
}

class Expense {
-Long id
-String description
-BigDecimal amount
-LocalDate date
-LocalDateTime createdAt
-User user
-Category category
+Expense()
+Expense(String description, BigDecimal amount, LocalDate date, User user, Category category)
+getId() Long
+getDescription() String
+getAmount() BigDecimal
+getDate() LocalDate
+getCreatedAt() LocalDateTime
+getUser() User
+getCategory() Category
+setDescription(String description) void
+setAmount(BigDecimal amount) void
+setDate(LocalDate date) void
+setUser(User user) void
+setCategory(Category category) void
+onCreate() void
+equals(Object o) boolean
+hashCode() int
+toString() String
}

class TransactionType {
<<enumeration>>
REVENUE
EXPENSE
+getValue() String
+fromValue(String value) TransactionType
}

    %% ===== DTOs =====

class UserDTO {
-Long id
-String name
-String email
-LocalDateTime createdAt
-LocalDateTime lastAccess
+UserDTO()
+UserDTO(User user)
+getId() Long
+getName() String
+getEmail() String
+getCreatedAt() LocalDateTime
+getLastAccess() LocalDateTime
+setId(Long id) void
+setName(String name) void
+setEmail(String email) void
+setCreatedAt(LocalDateTime createdAt) void
+setLastAccess(LocalDateTime lastAccess) void
}

class CreateUserDTO {
        -String name
        -String email
        +CreateUserDTO()
        +getName() String
        +getEmail() String
        +setName(String name) void
        +setEmail(String email) void
        +toEntity() User
        +isValid() boolean
    }

class UpdateUserDTO {
        -String name
        -String email
        +UpdateUserDTO()
        +getName() String
        +getEmail() String
        +setName(String name) void
        +setEmail(String email) void
        +isValid() boolean
    }

class UserWithCategoriesDTO {
        -Long id
        -String name
        -String email
        -LocalDateTime createdAt
        -List~CategoryDTO~ categories
        +UserWithCategoriesDTO()
        +UserWithCategoriesDTO(User user)
        +getId() Long
        +getName() String
        +getEmail() String
        +getCreatedAt() LocalDateTime
        +getCategories() List~CategoryDTO~
        +setCategories(List~CategoryDTO~ categories) void
    }

class CategoryDTO {
        -Long id
        -String name
        -TransactionType type
        -String icon
        -String color
        +CategoryDTO()
        +CategoryDTO(Category category)
        +getId() Long
        +getName() String
        +getType() TransactionType
        +getIcon() String
        +getColor() String
        +setId(Long id) void
        +setName(String name) void
        +setType(TransactionType type) void
        +setIcon(String icon) void
        +setColor(String color) void
    }

class CreateCategoryDTO {
        -String name
        -TransactionType type
        -String icon
        -String color
        +CreateCategoryDTO()
        +getName() String
        +getType() TransactionType
        +getIcon() String
        +getColor() String
        +setName(String name) void
        +setType(TransactionType type) void
        +setIcon(String icon) void
        +setColor(String color) void
        +toEntity(User user) Category
        +isValid() boolean
    }

class RevenueDTO {
        -Long id
        -String description
        -BigDecimal amount
        -LocalDate date
        -LocalDateTime createdAt
        -CategoryDTO category
        +RevenueDTO()
        +RevenueDTO(Revenue revenue)
        +getId() Long
        +getDescription() String
        +getAmount() BigDecimal
        +getDate() LocalDate
        +getCreatedAt() LocalDateTime
        +getCategory() CategoryDTO
        +setId(Long id) void
        +setDescription(String description) void
        +setAmount(BigDecimal amount) void
        +setDate(LocalDate date) void
        +setCreatedAt(LocalDateTime createdAt) void
        +setCategory(CategoryDTO category) void
    }

class CreateRevenueDTO {
        -String description
        -BigDecimal amount
        -LocalDate date
        -Long categoryId
        +CreateRevenueDTO()
        +getDescription() String
        +getAmount() BigDecimal
        +getDate() LocalDate
        +getCategoryId() Long
        +setDescription(String description) void
        +setAmount(BigDecimal amount) void
        +setDate(LocalDate date) void
        +setCategoryId(Long categoryId) void
        +toEntity(User user, Category category) Revenue
        +isValid() boolean
    }

class UpdateRevenueDTO {
        -String description
        -BigDecimal amount
        -LocalDate date
        -Long categoryId
        +UpdateRevenueDTO()
        +getDescription() String
        +getAmount() BigDecimal
        +getDate() LocalDate
        +getCategoryId() Long
        +setDescription(String description) void
        +setAmount(BigDecimal amount) void
        +setDate(LocalDate date) void
        +setCategoryId(Long categoryId) void
        +isValid() boolean
    }

class ExpenseDTO {
        -Long id
        -String description
        -BigDecimal amount
        -LocalDate date
        -LocalDateTime createdAt
        -CategoryDTO category
        +ExpenseDTO()
        +ExpenseDTO(Expense expense)
        +getId() Long
        +getDescription() String
        +getAmount() BigDecimal
        +getDate() LocalDate
        +getCreatedAt() LocalDateTime
        +getCategory() CategoryDTO
        +setId(Long id) void
        +setDescription(String description) void
        +setAmount(BigDecimal amount) void
        +setDate(LocalDate date) void
        +setCreatedAt(LocalDateTime createdAt) void
        +setCategory(CategoryDTO category) void
    }

class CreateExpenseDTO {
        -String description
        -BigDecimal amount
        -LocalDate date
        -Long categoryId
        +CreateExpenseDTO()
        +getDescription() String
        +getAmount() BigDecimal
        +getDate() LocalDate
        +getCategoryId() Long
        +setDescription(String description) void
        +setAmount(BigDecimal amount) void
        +setDate(LocalDate date) void
        +setCategoryId(Long categoryId) void
        +toEntity(User user, Category category) Expense
        +isValid() boolean
    }

class UpdateExpenseDTO {
        -String description
        -BigDecimal amount
        -LocalDate date
        -Long categoryId
        +UpdateExpenseDTO()
        +getDescription() String
        +getAmount() BigDecimal
        +getDate() LocalDate
        +getCategoryId() Long
        +setDescription(String description) void
        +setAmount(BigDecimal amount) void
        +setDate(LocalDate date) void
        +setCategoryId(Long categoryId) void
        +isValid() boolean
    }

class FinancialSummaryDTO {
        -BigDecimal totalRevenues
        -BigDecimal totalExpenses
        -BigDecimal balance
        -LocalDate startDate
        -LocalDate endDate
        -Long totalRevenueTransactions
        -Long totalExpenseTransactions
        +FinancialSummaryDTO()
        +FinancialSummaryDTO(BigDecimal totalRevenues, BigDecimal totalExpenses, LocalDate startDate, LocalDate endDate)
        +getTotalRevenues() BigDecimal
        +getTotalExpenses() BigDecimal
        +getBalance() BigDecimal
        +getStartDate() LocalDate
        +getEndDate() LocalDate
        +getTotalRevenueTransactions() Long
        +getTotalExpenseTransactions() Long
        +setTotalRevenues(BigDecimal totalRevenues) void
        +setTotalExpenses(BigDecimal totalExpenses) void
        +setStartDate(LocalDate startDate) void
        +setEndDate(LocalDate endDate) void
        +setTotalRevenueTransactions(Long totalRevenueTransactions) void
        +setTotalExpenseTransactions(Long totalExpenseTransactions) void
        +calculateBalance() void
        +hasPositiveBalance() boolean
        +hasNegativeBalance() boolean
    }

    %% ===== REPOSITORIES =====
class UserRepository {
        <<interface>>
        +findById(Long id) Optional~User~
        +findByEmail(String email) Optional~User~
        +findByIdWithCategories(Long id) Optional~User~
        +findByIdWithRevenues(Long id) Optional~User~
        +findByIdWithExpenses(Long id) Optional~User~
        +save(User user) User
        +deleteById(Long id) void
        +existsByEmail(String email) boolean
        +existsById(Long id) boolean
        +count() long
        +findAll() List~User~
    }

class CategoryRepository {
        <<interface>>
        +findByUserId(Long userId) List~Category~
        +findByUserIdAndType(Long userId, TransactionType type) List~Category~
        +findByIdAndUserId(Long id, Long userId) Optional~Category~
        +save(Category category) Category
        +deleteById(Long id) void
        +countByUserId(Long userId) Long
        +countByUserIdAndType(Long userId, TransactionType type) Long
        +existsByIdAndUserId(Long id, Long userId) boolean
        +findByUserIdOrderByName(Long userId) List~Category~
    }

class RevenueRepository {
        <<interface>>
        +findByUserId(Long userId) List~Revenue~
        +findByUserIdOrderByDateDesc(Long userId) List~Revenue~
        +findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate) List~Revenue~
        +findByUserIdAndCategoryId(Long userId, Long categoryId) List~Revenue~
        +findByIdAndUserId(Long id, Long userId) Optional~Revenue~
        +save(Revenue revenue) Revenue
        +deleteById(Long id) void
        +sumByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate) BigDecimal
        +countByUserId(Long userId) Long
        +countByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate) Long
        +existsByIdAndUserId(Long id, Long userId) boolean
        +findTop10ByUserIdOrderByDateDesc(Long userId) List~Revenue~
    }

class ExpenseRepository {
        <<interface>>
        +findByUserId(Long userId) List~Expense~
        +findByUserIdOrderByDateDesc(Long userId) List~Expense~
        +findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate) List~Expense~
        +findByUserIdAndCategoryId(Long userId, Long categoryId) List~Expense~
        +findByIdAndUserId(Long id, Long userId) Optional~Expense~
        +save(Expense expense) Expense
        +deleteById(Long id) void
        +sumByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate) BigDecimal
        +countByUserId(Long userId) Long
        +countByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate) Long
        +existsByIdAndUserId(Long id, Long userId) boolean
        +findTop10ByUserIdOrderByDateDesc(Long userId) List~Expense~
    }

    %% ===== SERVICES =====
class UserService {
        -UserRepository userRepository
        +findById(Long id) UserDTO
        +findByIdWithCategories(Long id) UserWithCategoriesDTO
        +findByEmail(String email) UserDTO
        +create(CreateUserDTO dto) UserDTO
        +update(Long id, UpdateUserDTO dto) UserDTO
        +delete(Long id) void
        +updateLastAccess(Long id) void
        +existsByEmail(String email) boolean
        +existsById(Long id) boolean
        +validateUserExists(Long id) User
        +getAllUsers() List~UserDTO~
        +getUserCount() long
    }

class CategoryService {
        -CategoryRepository categoryRepository
        -UserService userService
        +findByUser(Long userId) List~CategoryDTO~
        +findByUserAndType(Long userId, TransactionType type) List~CategoryDTO~
        +findById(Long id, Long userId) CategoryDTO
        +create(Long userId, CreateCategoryDTO dto) CategoryDTO
        +update(Long id, Long userId, CreateCategoryDTO dto) CategoryDTO
        +delete(Long id, Long userId) void
        +validateCategoryOwnership(Long categoryId, Long userId) Category
        +getCategoryCount(Long userId) Long
        +getCategoryCountByType(Long userId, TransactionType type) Long
        +createDefaultCategories(Long userId) List~CategoryDTO~
    }

class RevenueService {
        -RevenueRepository revenueRepository
        -UserService userService
        -CategoryService categoryService
        +findByUser(Long userId) List~RevenueDTO~
        +findByUserAndPeriod(Long userId, LocalDate startDate, LocalDate endDate) List~RevenueDTO~
        +findByUserAndCategory(Long userId, Long categoryId) List~RevenueDTO~
        +findById(Long id, Long userId) RevenueDTO
        +create(Long userId, CreateRevenueDTO dto) RevenueDTO
        +update(Long id, Long userId, UpdateRevenueDTO dto) RevenueDTO
        +delete(Long id, Long userId) void
        +calculateTotalByPeriod(Long userId, LocalDate startDate, LocalDate endDate) BigDecimal
        +getRevenueCount(Long userId) Long
        +getRecentRevenues(Long userId) List~RevenueDTO~
        +validateRevenueOwnership(Long revenueId, Long userId) Revenue
    }

class ExpenseService {
        -ExpenseRepository expenseRepository
        -UserService userService
        -CategoryService categoryService
        +findByUser(Long userId) List~ExpenseDTO~
        +findByUserAndPeriod(Long userId, LocalDate startDate, LocalDate endDate) List~ExpenseDTO~
        +findByUserAndCategory(Long userId, Long categoryId) List~ExpenseDTO~
        +findById(Long id, Long userId) ExpenseDTO
        +create(Long userId, CreateExpenseDTO dto) ExpenseDTO
        +update(Long id, Long userId, UpdateExpenseDTO dto) ExpenseDTO
        +delete(Long id, Long userId) void
        +calculateTotalByPeriod(Long userId, LocalDate startDate, LocalDate endDate) BigDecimal
        +getExpenseCount(Long userId) Long
        +getRecentExpenses(Long userId) List~ExpenseDTO~
        +validateExpenseOwnership(Long expenseId, Long userId) Expense
    }

class FinancialSummaryService {
        -RevenueService revenueService
        -ExpenseService expenseService
        +calculateBalance(Long userId, LocalDate startDate, LocalDate endDate) BigDecimal
        +getFinancialSummary(Long userId, LocalDate startDate, LocalDate endDate) FinancialSummaryDTO
        +getCurrentMonthSummary(Long userId) FinancialSummaryDTO
        +getYearSummary(Long userId, int year) FinancialSummaryDTO
        +getLastMonthsSummary(Long userId, int months) List~FinancialSummaryDTO~
    }

    %% ===== CONTROLLERS =====
class UserController {
        -UserService userService
        +getUser(Long id) ResponseEntity~UserDTO~
        +getUserWithCategories(Long id) ResponseEntity~UserWithCategoriesDTO~
        +createUser(CreateUserDTO dto) ResponseEntity~UserDTO~
        +updateUser(Long id, UpdateUserDTO dto) ResponseEntity~UserDTO~
        +deleteUser(Long id) ResponseEntity~Void~
        +getAllUsers() ResponseEntity~List~UserDTO~~
        +getUserCount() ResponseEntity~Long~
    }

class CategoryController {
        -CategoryService categoryService
        +getCategories(Long userId) ResponseEntity~List~CategoryDTO~~
        +getCategoriesByType(Long userId, TransactionType type) ResponseEntity~List~CategoryDTO~~
        +getCategory(Long id, Long userId) ResponseEntity~CategoryDTO~
        +createCategory(Long userId, CreateCategoryDTO dto) ResponseEntity~CategoryDTO~
        +updateCategory(Long id, Long userId, CreateCategoryDTO dto) ResponseEntity~CategoryDTO~
        +deleteCategory(Long id, Long userId) ResponseEntity~Void~
        +getCategoryCount(Long userId) ResponseEntity~Long~
        +createDefaultCategories(Long userId) ResponseEntity~List~CategoryDTO~~
    }

class RevenueController {
        -RevenueService revenueService
        +getRevenues(Long userId) ResponseEntity~List~RevenueDTO~~
        +getRevenuesByPeriod(Long userId, LocalDate startDate, LocalDate endDate) ResponseEntity~List~RevenueDTO~~
        +getRevenue(Long id, Long userId) ResponseEntity~RevenueDTO~
        +createRevenue(Long userId, CreateRevenueDTO dto) ResponseEntity~RevenueDTO~
        +updateRevenue(Long id, Long userId, UpdateRevenueDTO dto) ResponseEntity~RevenueDTO~
        +deleteRevenue(Long id, Long userId) ResponseEntity~Void~
        +getTotalByPeriod(Long userId, LocalDate startDate, LocalDate endDate) ResponseEntity~BigDecimal~
        +getRevenueCount(Long userId) ResponseEntity~Long~
        +getRecentRevenues(Long userId) ResponseEntity~List~RevenueDTO~~
    }

class ExpenseController {
        -ExpenseService expenseService
        +getExpenses(Long userId) ResponseEntity~List~ExpenseDTO~~
        +getExpensesByPeriod(Long userId, LocalDate startDate, LocalDate endDate) ResponseEntity~List~ExpenseDTO~~
        +getExpense(Long id, Long userId) ResponseEntity~ExpenseDTO~
        +createExpense(Long userId, CreateExpenseDTO dto) ResponseEntity~ExpenseDTO~
        +updateExpense(Long id, Long userId, UpdateExpenseDTO dto) ResponseEntity~ExpenseDTO~
        +deleteExpense(Long id, Long userId) ResponseEntity~Void~
        +getTotalByPeriod(Long userId, LocalDate startDate, LocalDate endDate) ResponseEntity~BigDecimal~
        +getExpenseCount(Long userId) ResponseEntity~Long~
        +getRecentExpenses(Long userId) ResponseEntity~List~ExpenseDTO~~
    }

class FinancialSummaryController {
        -FinancialSummaryService financialSummaryService
        +getFinancialSummary(Long userId, LocalDate startDate, LocalDate endDate) ResponseEntity~FinancialSummaryDTO~
        +getBalance(Long userId, LocalDate startDate, LocalDate endDate) ResponseEntity~BigDecimal~
        +getCurrentMonthSummary(Long userId) ResponseEntity~FinancialSummaryDTO~
        +getYearSummary(Long userId, int year) ResponseEntity~FinancialSummaryDTO~
        +getLastMonthsSummary(Long userId, int months) ResponseEntity~List~FinancialSummaryDTO~~
    }

    %% ===== EXCEPTION CLASSES =====
class UserNotFoundException {
        +UserNotFoundException(String message)
        +UserNotFoundException(Long userId)
    }

class CategoryNotFoundException {
        +CategoryNotFoundException(String message)
        +CategoryNotFoundException(Long categoryId)
    }

class RevenueNotFoundException {
        +RevenueNotFoundException(String message)
        +RevenueNotFoundException(Long revenueId)
    }

class ExpenseNotFoundException {
        +ExpenseNotFoundException(String message)
        +ExpenseNotFoundException(Long expenseId)
    }

class ValidationException {
        +ValidationException(String message)
        +ValidationException(String message, Throwable cause)
    }

