#include <IRremote.h> //bibliotecad do infravermelho

int sensorFotoTransistor = 5; //pino aonde estará o infravermelho

//variavel do tipo receptor infra que recebe o pino como argumento
IRrecv irRecv(sensorFotoTransistor);

//o resultado da informação recebida do infravermelho
decode_results results;

void setup() {
  Serial.begin(9600);
  irRecv.enableIRIn();
  Serial.println("setup");
}

void loop() {
  //https://www.pjrc.com/teensy/td_libs_IRremote.html
  //se tiver informação coloca na variavel "results"
  if (irRecv.decode(&results)) {
    if (results.decode_type == NEC) {
      Serial.print("NEC: ");
    } else if (results.decode_type == SONY) {
      Serial.print("SONY: ");
    } else if (results.decode_type == RC5) {
      Serial.print("RC5: ");
    } else if (results.decode_type == RC6) {
      Serial.print("RC6: ");
    } else if (results.decode_type == UNKNOWN) {
      Serial.print("UNKNOWN: ");
    }
    Serial.println(results.value, HEX);
    irRecv.resume(); // Receive the next value
  }
  delay(100);
}
