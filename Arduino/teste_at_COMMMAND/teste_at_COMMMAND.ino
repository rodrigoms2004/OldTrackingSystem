#include <SoftwareSerial.h>

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




SoftwareSerial myGSM(rxPin, txPin);
unsigned char buffer[64]; // buffer array for data recieve over serial port
int count=0;     // counter for buffer array 

// http://www.seeedstudio.com/wiki/GPRS_Shield_V1.0

void setup() {
  
  Serial.begin(19200);
  Serial.println("Initializing...");
  myGSM.begin(19200);
  
}  // end setup

void loop() {
  // put your main code here, to run repeatedly:
  if (myGSM.available()) 
  {
    while(myGSM.available()) 
    {
      buffer[count++] = myGSM.read();  
      if (count == 64) break;
    }  // end while
    Serial.write(buffer, count);
    clearBufferArray();
    count = 0;
 
  }  // end if
  if (Serial.available())
    myGSM.write(Serial.read());
  
}  // end loop

void clearBufferArray()
{
  for (int i = 0; i < count; i++)
  {
    buffer[i] = NULL;
  }

}

