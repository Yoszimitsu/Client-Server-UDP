### Communication Client-Server UDP ###
####Description####
The Server offers a text
transformation service with two functionalities: 
1. removal of tabs and extra spaces,
2. removal of tabs and extra spaces with random re-ordering of the words.

#####Client specification:######
* The client should accept as input parameters both the server’s IP address and port
number. For example: java ClientUDP 192.168.166.121 12345
* Once the client is connected to the server, it will receive a message that indicates that
the client is connected to the service.
* Once the client receives this message, it should request two input textual lines to the
user. The first line is the key of the service that the client requests and the second is the
text to be transformed. These two lines should be concatenated and sent to the server.
* After each message delivery, the client should wait for the server’s response, and print it
in the output console. This response could be an error message or the transformed text.
For example, if the client sends ‘b’ and “Hello, how are you doing?”, the
server could answer “are you Hello, how doing?”.
* We assume that the information sent by the client is correct and the program does not
need to check the text.
* When the user decides to finish the service, it should write ‘F’. Take into consideration
that when the user introduce ‘F’ as letter, the program will not read the following text.
* When the client reads ‘F’ from the standard input, ClientUDP finishes its execution (it
does not send any information to the server).
* The client should print out appropriate informative messages about its state during the
session as it executes (e.g. Connected to 192.168.167.2, Waiting for a
response, …)

#####Server specification##### 
* The server should accept as input parameter the port number for accepting connections.
For example: java ServerUDP 12345
* The server is always waiting for incoming datagrams that includes a letter (the
transformation service selected) and the text to be transformed.
*The size of the reception buffer should be 20 bytes.
* The server sends the transformed text to the client that sent the datagram.
* The answer of the server depends on the option selected by the client:
    * L: The server remove extra spaces and tabs. The result is a text with only one
space between each word.
    * B: In addition to the functionality of the L option, this service randomly re-order
the text.
    * Any other option: The server send “Not supported option”.
* As the client, the server should print out appropriate informative messages about its state
as it executes (e.g., Waiting for a new client …).