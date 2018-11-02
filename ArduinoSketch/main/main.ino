/*
  BuildingAutomation
  Aplicação com o proposito de coordenar ativação e desativação
  automática de eletro-eletronicos em predios comerciais
*/

#include "IRremote.h" //biblioteca do controle remoto

//setar os sensores
const int sensorPIR = 3;
const int sensorTemperatura = 4;

//setar os atuadores
const int atuadorRele = 6;
const int atuadorControleInfravermelho = 7;
IRsend irSend;

//constantes utilizadas
const unsigned long comandoDeLigarArCondicionado = 0x39C600FF; //exemplo: 0x39C600FF
const unsigned long comandoDeDesligarArCondicionado;


//setar variaveis de controle
String serialReturn;
int statusSensorPIR = 0;
int statusSensorTemperatura = 0;

void setup() {
  Serial.begin(9600); //comunicação Serial

  //sensores
  pinMode( sensorPIR, INPUT );
  //o sensorTemperatura é analogico
  
  //atuadores
  pinMode( atuadorRele, OUTPUT );
  pinMode( atuadorControleInfravermelho, OUTPUT );
}

void loop() {
  ligarEletronicos();
  desligarEletronicos();
}

}
