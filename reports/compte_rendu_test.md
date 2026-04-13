# Compte-rendu de test — Lot 1.1
## Projet ToutAvis — FIP INF112

**Date :** 03/04/2026  
**Itération :** Lot 1.1 — Gestion des membres  
**Méthodes testées :** `addMember()`, `nbMembers()`, `toString()`

---

## Sortie console

```
Testing brand new Social Networks...
Here is the newly created SocialNetwork :
SocialNetwork : [0 membre(s), 0 film(s), 0 livre(s)]
Membres :
InitTest : [ Nb of performed tests : 3 / nb of detected error(s) : 0 ]


 **********************************************************************************************

Testing addMember()
Final state of the social network : SocialNetwork : [3 membre(s), 0 film(s), 0 livre(s)]
Membres : Paul Antoine Alice
AddMemberTest : [ Nb of performed tests : 15 / nb of detected error(s) : 0 ]


 **********************************************************************************************

Global tests results :
[ Nb of performed tests : 18 / nb of detected error(s) : 0 ]
```

---

## Récapitulatif des cas de test

### InitTest — Initialisation du SocialNetwork (3 tests)

| ID  | Description                                      | Résultat attendu | Résultat observé |
|-----|--------------------------------------------------|------------------|------------------|
| 0.1 | nbMembers() sur un nouveau SocialNetwork         | 0                | OK               |
| 0.2 | nbBooks() sur un nouveau SocialNetwork           | 0                | OK               |
| 0.3 | nbFilms() sur un nouveau SocialNetwork           | 0                | OK               |

### AddMemberTest — Fiche 1 : BadEntryException (5 tests)

| ID  | Description                                              | Résultat attendu    | Résultat observé |
|-----|----------------------------------------------------------|---------------------|------------------|
| 1.1 | addMember avec login null                                | BadEntryException   | OK               |
| 1.2 | addMember avec login = " " (espaces uniquement)          | BadEntryException   | OK               |
| 1.3 | addMember avec password null                             | BadEntryException   | OK               |
| 1.4 | addMember avec password = "   qwd " (3 chars hors blancs)| BadEntryException   | OK               |
| 1.5 | addMember avec profile null                              | BadEntryException   | OK               |

> Vérification complémentaire : après chaque BadEntryException, nbMembers() reste inchangé.

### AddMemberTest — Fiche 2 : Cas nominal + MemberAlreadyExistsException (10 tests)

| ID  | Description                                                        | Résultat attendu                  | Résultat observé |
|-----|--------------------------------------------------------------------|-----------------------------------|------------------|
| 2.1a| addMember("Paul", "paul", "lecteur impulsif")                      | Ajout OK, nbMembers = 1           | OK               |
| 2.1b| addMember("Antoine", "antoine", "grand amoureux de la littérature")| Ajout OK, nbMembers = 2           | OK               |
| 2.1c| addMember("Alice", "alice", "passionnée de bande dessinée")        | Ajout OK, nbMembers = 3           | OK               |
| 2.2 | addMember avec login = "Paul" (premier membre)                     | MemberAlreadyExistsException      | OK               |
| 2.3 | addMember avec login = "Alice" (dernier membre)                    | MemberAlreadyExistsException      | OK               |
| 2.4 | addMember avec login = "anToine" (casse différente)                | MemberAlreadyExistsException      | OK               |
| 2.5 | addMember avec login = " Antoine " (leading/trailing blanks)       | MemberAlreadyExistsException      | OK               |
| 2.6 | addMember avec login = "An"+"toi"+"ne" (concaténation)             | MemberAlreadyExistsException      | OK               |
| —   | nbFilms() inchangé après toutes les opérations addMember()         | 0                                 | OK               |
| —   | nbBooks() inchangé après toutes les opérations addMember()         | 0                                 | OK               |

> Vérification complémentaire : après chaque MemberAlreadyExistsException, nbMembers() reste inchangé.

---

## Conclusion

| Classe de test     | Tests exécutés | Erreurs détectées |
|--------------------|----------------|-------------------|
| InitTest           | 3              | 0                 |
| AddMemberTest      | 15             | 0                 |
| **Total**          | **18**         | **0**             |

**Tous les tests passent. Les objectifs du lot 1.1 sont atteints.**

Les méthodes `addMember()`, `nbMembers()` et `toString()` de la classe `SocialNetwork` sont conformes à l'interface `ISocialNetwork` imposée par le client BibTel.
