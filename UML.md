# UML

```mermaid
erDiagram
    Media ||--|{ Music : contains
    Media ||--|{ Video : contains
    Media ||--|{ Game  : contains
    
    Media }o--o{ Genre : categorized_as

    Music }o--|| Duration : has_duration
    Video }o--|| Duration : has_duration

    Media {
        string id
        string title
        int    rating
    }

    Music {
        string id
        string artist
        %% no extra duration field here, we link to Duration instead
    }

    Video {
        string id 
        string resolution
        int    length   
    }

    Game {
        string id
        string platform
    }

    Genre {
        string id
        string name
    }

    Duration {
        int   minutes
        int   seconds
    }

```

Eine kleine persönliche Medien Kollektion. Damit kann ich meine persönliche Medien Katalogisieren und bewerten. Es schafft mir auch einen Überblick mit allen nötigen Informationen. Ich könnte z.B nach dem Rating ordnen und so einen Überblick über meine beliebtesten Medien schaffen.


## Authentication und Authorization

Nachfolgend drei Sequenzdiagramme, welche den kompletten Fluss einer Anfrage von der Auslösung im Browser bis in die Datenbank und zurück zeigen. Die Varianten sind so aufgebaut, dass sowohl JWT (Token im `Authorization` Header) als auch klassische Session-Cookies unterstützt werden können – die Unterscheidung passiert im Auth-Service (z.B. durch Signaturprüfung bei JWT oder durch Lookup einer Session-Tabelle).

### Anmeldung (Login)

```mermaid
sequenceDiagram
    autonumber
    actor Browser
    participant API as Backend API
    participant Auth as Auth-Service
    participant DB as Datenbank

    Browser->>API: POST /login (User+Pass)
    API->>Auth: validateCredentials(user, hash(pass))
    Auth->>DB: SELECT user, password_hash
    DB-->>Auth: Userdatensatz
    Auth-->>API: Resultat + Token/Cookie
    API-->>Browser: HTTP 200 + JWT oder HTTP-Only-Cookie
    Browser->>Browser: Persist Token (LocalStorage) oder Cookie
```

### Abmelden (Logout)

```mermaid
sequenceDiagram
    autonumber
    actor Browser
    participant API as Backend API
    participant Auth as Auth-Service
    participant DB as Datenbank

    Browser->>API: POST /logout (Token/Cookie)
    API->>Auth: invalidate(token|sessionId)
    Auth->>DB: UPDATE session SET revoked=true
    DB-->>Auth: OK
    Auth-->>API: Logout bestätigt
    API-->>Browser: HTTP 204, Browser löscht Token oder Cookie
```

### Authentifizierung bei einer Datenabfrage (z.B. Zeiteintrag erfassen)

```mermaid
sequenceDiagram
    autonumber
    actor Browser
    participant API as Backend API
    participant Auth as Auth-Service
    participant Domain as TimeEntry-Service
    participant DB as Datenbank

    Browser->>API: POST /time-entries (Token/Cookie + Payload)
    API->>Auth: verify(token|cookie)
    Auth->>DB: SELECT session/user claims
    DB-->>Auth: Claims gültig
    Auth-->>API: Authenticated userId
    API->>Domain: createEntry(userId, payload)
    Domain->>DB: INSERT time_entry
    DB-->>Domain: gespeicherter Datensatz
    Domain-->>API: Ergebnis
    API-->>Browser: HTTP 201 + Response-Body
```

