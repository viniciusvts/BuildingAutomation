/*
  BuildingAutomation
  Aplicação com o proposito de coordenar ativação e desativação
  automática de eletro-eletronicos em predios comerciais
*/

String serialReturn = "teste"; //caso a variável não seja alterada, emitirá "teste" no monitor

void setup() {
  Serial.begin(9600);
}

void loop() {
  if(Serial.available() > 0){
    serialReturn = Serial.readString();
  }
  delay(1000);
  if(serialReturn != ""){
    Serial.print(serialReturn);
    serialReturn = "";
  }else{
    Serial.print("none");
  }
  delay(1000);
  
}
