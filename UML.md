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