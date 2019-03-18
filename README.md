# Minder_Server
Servidor del Minder

TODO LIST:

  - Crear un thread que està escoltant peticions d'usuaris continuament per registrar-los (actiu en tot moment).
  
  - El servidor comprova durant el registre:
    - Tot el que comprova el client.
    - Que el nom d'usuari i correu no estiguin prèviament reistrats.
    
  - El servidor comprova durant l'autentificació:
    - Mirar que l'user o el mail constin en la base de dades.
    - Mirar que la password coincideixi.
    - Retornar satisfactori o insatisfactori (client mostra missatge).
    
  - Emmagatzemament i consulta de dades dels usuaris en la base de dades.
    - Actualització a temps real de les dades (si estic mirant un perfil i aquest canvia algun paràmetre, he de veure el canvi al moment).
    - Guarda els històrics dels usuaris (si dos usuaris han fet match i després unmatch).
    
  - Gestionar connexions:
    - Guarda quins usuaris han estat acceptats per altres i matchs (acceptacions mútues).
  
  - Gestionar xats:
    - Si es dóna match, es pot obrir xat.
    - Guardar els missatges del xat (si un usuari es desconnecta i connecta, ha de tenir accés als missatges del xat).
    
  - Mostrar evolució nombre de matchs:
    - Accessible des de la GUI del servidor.
    - Gràfic de línies nombre de matches respecte el temps.
      - Últim dia.
      - Última setmana.
      - Últim mes.
      - Escalat en funció de màxim i mínim de Y (matchs).
     
  - Mostrar top 5 usuaris més acceptats:
    - Accessible des de la GUI del servidor.
    - Taula amb top 5 users més acceptats:
        - Ordenats descendentment.
        - Nom + nº matchs.
        
        
  
OPCIONALS:
  - Registrar usuaris des de la GUI del server, amb mateixos requeriments que client.
  - Mostrar % d'acceptats vs visualitzats.
  
