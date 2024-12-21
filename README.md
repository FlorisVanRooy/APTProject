# APTProject

## Overzicht
APTProject is een backend-gebaseerd systeem voor het beheren van evenementen, gebruikersregistraties en ticketverkoop. Het is opgebouwd met behulp van moderne technologieën zoals Java, JavaScript, CSS en Docker. Dit project maakt gebruik van een microservices-architectuur, waarin verschillende services zoals een API Gateway, Event Service en Registration Service naadloos samenwerken om een efficiënte en schaalbare oplossing te bieden voor event- en ticketbeheer.

## Kenmerken
- **API Gateway**: Beheert de communicatie tussen verschillende services in het systeem.
- **Event Service**: Behandelt alle logica met betrekking tot evenementen, zoals het aanmaken, bijwerken en ophalen van evenementgegevens.
- **Ticket Service**: Behandelt alle logica met betrekking tot tickets, zoals het aanmaken, bijwerken en ophalen van ticketgegevens.
- **Registration Service**: Verantwoordelijk voor gebruikersregistratie, ticketboekingen en gebruikersbeheer.
- **Docker**: Maakt het mogelijk om de verschillende services in containers te draaien, zodat ze gemakkelijk geïsoleerd en geüpdatet kunnen worden.

## Technologieën
- **Java**: Voor het ontwikkelen van de backend services.
- **Docker**: Voor containerisatie van de microservices en het opzetten van de ontwikkelomgeving.
- **Spring Boot**: Voor het bouwen van de backend microservices.
- **MongoDB**: Voor het opslaan van gegevens. Deze is gebruikt bij de evenementen omdat er veel 'read' instructies op gebeuren en deze kunnen dan sneller uitgevoerd worden.
- **SQL**: Voor het opslaan van gegevens. Deze is gebruikt omwille van de stabiliteit en integriteit van de data en relaties.

## Installatie

Volg de onderstaande stappen om het project lokaal te draaien:

### 1. Repository Clonen
Clone het project naar je lokale machine:
git clone https://github.com/FlorisVanRooy/APTProject.git

### 2. Docker Installeren
Zorg ervoor dat Docker op je machine is geïnstalleerd. Als je Docker nog niet hebt geïnstalleerd, kun je dit doen via de [officiële Docker-website](https://www.docker.com/get-started).

### 3. Docker Containers Opstarten
Navigeer naar de map van het project en start de benodigde Docker containers:
docker-compose up --build

Dit zorgt ervoor dat alle services (API Gateway, Event Service, Registration Service) correct worden opgezet en uitgevoerd.

### 4. Services Benaderen
Na het opstarten van de containers kun je de services benaderen via hun respectieve API's. Raadpleeg de documentatie van de API voor specifieke eindpunten en interacties.

## Gebruik

Na de succesvolle installatie en het opstarten van de Docker-containers kun je beginnen met het gebruiken van de verschillende services:

- **Event Service**: Maak, wijzig of haal evenementen op.
- **Ticket Service**: Maak, wijzig of haal tickets op.
- **Registration Service**: Registreer gebruikers en boek tickets.

De API-eindpunten zijn te benaderen via de API Gateway. Dit biedt een centrale toegangspoort voor alle verzoeken naar de verschillende microservices.

## Authenticatie met OAuth

APTProject maakt gebruik van OAuth 2.0 voor veilige authenticatie en autorisatie. Gebruikers kunnen zich aanmelden via externe OAuth-providers, zoals Google, GitHub, of een andere gekozen provider. Dit zorgt ervoor dat gebruikers op een veilige manier toegang kunnen krijgen tot de applicatie zonder dat ze hun wachtwoorden direct hoeven in te voeren. In dit project is enkel in te loggen via Google.

De authenticatieflow werkt als volgt:
1. De gebruiker wordt omgeleid naar de OAuth-provider voor inloggen.
2. Na succesvolle authenticatie ontvangt de applicatie een toegangstoken.
3. Dit toegangstoken wordt gebruikt voor toegang tot de beveiligde API-eindpunten.

Deze aanpak zorgt voor een veiliger en eenvoudiger gebruikersbeheer. Het implementeren van OAuth helpt ook om te voldoen aan best practices voor beveiliging zonder zelf gevoelige gebruikersgegevens zoals wachtwoorden te beheren.

