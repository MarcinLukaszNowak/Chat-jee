Projekt należy uruchomić na serwerze. W trakcie jego tworzenia był używany Wildfly, podobnie jak na kursie.

Aplikację klienta można uruchomić przy pomocy metody main z klasy ClientApp. Po uruchomieniu aplikacja zapyta o login,
a następnie dołączy użytkownika do głównego pokoju 'main'. W celu wykonania innej akcji niż przesłanie wiadomości do
osób wewnątrz pokoju można użyć jednej z komend. Komendy zaczyną się na "**". Ich pełna lista wraz z opisem
zostanie wypisana po wysłaniu wiadomości o treści: ""**help".

Historia konwersacji zapisuje się w mySQLu.

Konwersacja między użytkownikami bazuje na JMS. Wysyłanie, pobieranie plików oraz wyciąganie historii rozmów
bazuje na protokole HTTP.



