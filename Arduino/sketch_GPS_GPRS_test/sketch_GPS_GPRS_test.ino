#include <SoftwareSerial.h>

// sudo chmod a+rw /dev/ttyACM0


int txPin = 11;
int rxPin = 10;
int modGSMPin = 6;
int pinDelay = 3000;

char apnName[20] = "zap.vivo.com.br";
char apnUser[5] = "vivo";
char apnPass[5] = "vivo";
char ip_data[40] = "39.549,44.223,12";
char aux_str[100];
char ip_server[40] = "54.232.230.116";
char port_server[10] = "5005";

int8_t answer;
int8_t attachStatus;

SoftwareSerial myGSM(rxPin, txPin);

void setup() {
  pinMode(txPin, OUTPUT);
  pinMode(rxPin, INPUT); // used to turn on and off GSM
  pinMode(modGSMPin, OUTPUT);
  Serial.begin(9600);
  Serial.println("Initializing...");
  myGSM.begin(9600);
//  Serial.println("testeA");
  //delay(1000);
  powerOnGSM();
  //delay(1000);
//  Serial.println("testeB");
  Serial.println("Connecting..."); // checks for connection I believe
  while ((sendATcommand2("AT+CREG?", "+CREG: 0,1", "ERROR", 500) || sendATcommand2("AT+CREG?", "+CREG: 0,5", "ERROR", 500)) == 0);
  // 0 = not registered, not searching
  // 1 = registered on home network
  // 2 = not registered, but searching
  // 3 = registration denied
  // 4 = unknown (out of coverage?)
  // 5 = roaming, registered
  // 6 = SMS only, home network
  // 7 = SMS only, roaming
  // 8 = EMS
  // 9 = CSFB not preferred, home network
  // 10 = CSBFB not preferred, roaming  
  sendATcommand2("AT+CREG=?", "OK", "ERROR",1000); // verifies network list
  sendATcommand2("AT+GSN","OK", "ERROR", 5000); // IEMI
  delay(1000);

}	// end void setup

void loop() {
	//Serial.println("Seding...");
        //dataCall();
     //   delay(5000);
}	// end void loop()


void dataCall(){
// Selects Single-connection mode
  if (sendATcommand2("AT+CIPMUX=0", "OK", "ERROR", 1000) == 1)
  {
    // Waits for status IP INITIAL
    while(sendATcommand2("AT+CIPSTATUS", "INITIAL", "", 500)  == 0 );
    delay(5000);
      // Sets the APN, user name and password
      sprintf(aux_str,"AT+CSTT=\"%s\",\"%s\",\"%s\"", apnName,apnUser,apnPass);  
      if (sendATcommand2(aux_str , "OK",  "ERROR", 30000) == 1)
      {            
        // Waits for status IP START
        while(sendATcommand2("AT+CIPSTATUS", "START", "", 500)  == 0 );
        delay(5000);
          // Brings Up Wireless Connection
          if (sendATcommand2("AT+CIICR", "OK", "ERROR", 30000) == 1)
          {
            // Waits for status IP GPRSACT
            while(sendATcommand2("AT+CIPSTATUS", "GPRSACT", "", 500)  == 0 );
            delay(5000);
              // Gets Local IP Address
              if (sendATcommand2("AT+CIFSR", ".", "ERROR", 10000) == 1)
              {
                // Waits for status IP STATUS
                while(sendATcommand2("AT+CIPSTATUS", "IP STATUS", "", 500)  == 0 );
                delay(5000);
                Serial.println("Openning TCP");
                  // Opens a TCP socket
                  //sprintf(aux_str, "AT+CIPSTART=\"TCP\",\"%s\",\"%s\"",ip_server, port_server);
                  if (sendATcommand2("AT+CIPSTART=\"TCP\",\"54.232.230.116\",\"5005\"", "CONNECT OK", "CONNECT FAIL", 30000) == 1)
                  //if (sendATcommand2(aux_str, "CONNECT OK", "CONNECT FAIL", 30000) == 1)
                  {
                    Serial.println("Connected");
                    // Sends some data to the TCP socket
                    do
                    {
                      sprintf(aux_str,"AT+CIPSEND=%d", strlen(ip_data));
                      Serial.println("IP_DATA VALUE IS:");
                      Serial.println(ip_data);
                      if (sendATcommand2(aux_str, ">", "ERROR", 10000) == 1)    
                      {
                        sendATcommand2(ip_data, "SEND OK", "ERROR", 10000);
                        delay(3000);
                      }
                    }
                    while(sendATcommand2("AT+CIPSTART=?", ".", "", 50)  == 0 );
                    // Closes the socket
                    sendATcommand2("AT+CIPCLOSE", "CLOSE OK", "ERROR", 10000);
                  }
                  else
                  {
                    Serial.println("Error openning the connection");
                  }  
              }
              else
              {
                Serial.println("Error getting the IP address");
              }  
          }
          else
          {
            Serial.println("Error bring up wireless connection");
          }
      }
      else
      {
        Serial.println("Error setting the APN");
      } 
  }
  else
  {
    Serial.println("Error setting the single connection");
  }
  sendATcommand2("AT+CIPSHUT", "OK", "ERROR", 10000);
  delay(10000);
}

// void hangUp() {
//   sendATcommand2("ATH", "OK", "ERROR",3000);
// }

// void sendSMS(char *message, char *number) {
//  Serial.println("Sending SMS...");
//  sendATcommand("AT+CMGF=1", "OK", 1000); // prep SMS mode
//  sprintf(aux_str,"AT+CMGS=\"%S\"", number);  
//   myGSM.println(aux_str);
//   // answer = sendATcommand(aux_str,">", 2000);
//   // answer = sendATcommand(aux_str,"OK",1000);
//   if (answer == 1) {
//     // myGSM.println(message);
//     myGSM.println("Test message.");
//     myGSM.write(0x1A);
//     answer = sendATcommand("", "OK", 20000);
//     if (answer == 1) {
//       Serial.println("Sent.");
//       } else {
//         Serial.println("Error");
//       }
//       } else {
//         Serial.print("Error ");
//         Serial.println(answer, DEC);
//       }
//     }
//     void dial(char *number) {
//       Serial.println("Dialing phone number...");
//       sprintf(aux_str, "ATD%s;", number);
//   sendATcommand(aux_str, "OK", 10000); // dial
// }

void powerOnGSM() {
  uint8_t answer = 0;
  // check if the module is started
  answer = sendATcommand2("AT", "OK", "ERROR", 5000);
  //if no answer
  if (answer == 0)
  {
    // Send power on pulse
    // digitalWrite(modGPSPin, LOW);
    if (digitalRead(modGSMPin) == HIGH){
      digitalWrite(modGSMPin, LOW);  
      delay(1200);
      digitalWrite(modGSMPin, HIGH);  
    }
    else
    {
      digitalWrite(modGSMPin, HIGH);  
      delay(10000);
    }
    // wait for answer
    while (answer == 0 ) 
    {
      answer = sendATcommand2("AT", "OK", "ERROR", 2000);
    }
  }
}

int8_t sendATcommand2(char* ATcommand, char* expected_answer1, char* expected_answer2, unsigned int timeout){
  uint8_t x=0,  answer=0;
  char response[100];
  unsigned long previous;
  memset(response, '\0', 100);    // Initialize the string
  delay(100);
  while( myGSM.available() > 0) myGSM.read();    // Clean the input buffer
    myGSM.println(ATcommand);    // Send the AT command 
    x = 0;
    previous = millis();
    // this loop waits for the answer
    do
    {
      // if there are data in the UART input buffer, reads it and checks for the asnwer
      if(myGSM.available() != 0)
      {    
        response[x] = myGSM.read();
        x++;
      }
      // Serial.println(myGSM.available() != 0);
    }
    while(((millis() - previous) < timeout));     // Waits for the asnwer with time out
    // check if the desired answer 1  is in the response of the module
    if (strstr(response, expected_answer1) != NULL)    
    {
      answer = 1;
    }
    // check if the desired answer 2 is in the response of the module
    else if (strstr(response, expected_answer2) != NULL)    
    {
      answer = 2;
    }
    Serial.println(response);
  return answer;
}
