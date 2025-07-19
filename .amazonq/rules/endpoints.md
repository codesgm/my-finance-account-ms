lista de endpoints

GET    /api/users/{id}
POST   /api/users
PUT    /api/users/{id}
DELETE /api/users/{id}

# Categories
GET    /api/users/{userId}/categories
GET    /api/users/{userId}/categories?type=REVENUE|EXPENSE
POST   /api/users/{userId}/categories
PUT    /api/users/{userId}/categories/{id}
DELETE /api/users/{userId}/categories/{id}

# Revenues
GET    /api/users/{userId}/revenues
GET    /api/users/{userId}/revenues?startDate=&endDate=
POST   /api/users/{userId}/revenues
PUT    /api/users/{userId}/revenues/{id}
DELETE /api/users/{userId}/revenues/{id}

# Expenses
GET    /api/users/{userId}/expenses  
GET    /api/users/{userId}/expenses?startDate=&endDate=
POST   /api/users/{userId}/expenses
PUT    /api/users/{userId}/expenses/{id}
DELETE /api/users/{userId}/expenses/{id}
