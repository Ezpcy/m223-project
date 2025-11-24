Example of an **isolation failure**:

```mermaid
sequenceDiagram
	autonumber
	actor User1
	actor User2
    participant API as Backend API	
	participant DB as Datenbank
	
	User1->>API: /POST Create new media collection
	User2->>API: /POST Create new media collection
	API->>DB: Create Media collection with id 10
	API->>DB: Create Media collection with id 10
	DB->>API: Simultaneous request to create a media collection with id 10 fails
	DB->API: Rollback transaction
```

