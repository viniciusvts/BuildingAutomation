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
const String comandoStringParaLigarTudo = "l";
const String comandoStringParaDesligarTudo = "d";


//setar variaveis de controle
bool estadoAr = false; // Controle de estado do ar condicionado
bool jaEnviouLigar = false;
bool jaEnviouDesligar = false;
String serialReturn;
int statusSensorPIR = 0;
int statusSensorTemperatura = 0;
unsigned int millisAnterior = 0;
unsigned int millisAtual = millis(); // Duração de 50 dias
const long intervalo5min = 300000;

void setup() {
  Serial.begin(9600); //comunicação Serial

  //sensores
  pinMode( sensorPIR, INPUT );
  //o sensorTemperatura é analogico
  
  //atuadores
  pinMode( atuadorRele, OUTPUT );
  pinMode( atuadorControleInfravermelho, OUTPUT );
  receberTodosOsDadosExternos(); 
}

void loop() { 
    // Premissa maxima para evento. Os eventos so poderão acontecer no horário 
<<<<<<< HEAD
 if(isTempoEvento()){
    if(serialTratada.equals("STRING PARA LIGAR")){
          if(isPresencaOn()){
                if(estadoAr == false){
                    ligarEletronicos();
                }else if(estadoAr == true){
                    if(statusSensorTemperatura > 23){
                        delay(300000); // esperar 5 min #### Delay para o processamento da CPU verificar outra maneira de esperar.
                    } // Testar se está gelando
                    if(statusSensorTemperatura > 14 && statusSensorTemperatura < 24){
                        delay(10000); // Sensor vai ler temperatura de 10 em 10 Segundos.
                    }

                }
          }
    }else if(serialTratada.equals("STRING PARA DESLIGAR")){
          if(estadoAr == true){      
              desligarEletronicos();
              
          }else if(estadoAr == false){
              if(statusSensorTemperatura > 23){
                delay(300000);                
              }
              if(statusSensorTemperatura > 14 && statusSensorTemperatura <24){
                delay(300000);
              }
          }
             
    }
  }
}                    
=======
    if(serialReturn.equals(comandoStringParaLigarTudo) || jaEnviouLigar){
        // ligarEletronicos(); // Verificar 
        if(isPresencaOn()){
            if(estadoAr == false){
                ligarEletronicos();
            }else if(estadoAr == true){
                 if(millisAtual - millisAnterior >= intervalo5min){ 
                    if(statusSensorTemperatura > 23){
                      ligarEletronicos();
                      millisAnterior = millisAtual; // Verificar temp em 5 minutos.
                                                        
                    }
                  }
            }
        }
    }else if(serialReturn.equals(comandoStringParaDesligarTudo) || jaEnviouDesligar){
        if(estadoAr == true){      
            desligarEletronicos();
        }else if(estadoAr == false){
            if(millisAtual - millisAnterior >= intervalo5min){ 
                    if(statusSensorTemperatura < 23){
                      desligarEletronicos();
                      millisAnterior = millisAtual; // Verificar temp de 5 em 5 minutos.
                    }
                                       
             }
        }
    }
    receberTodosOsDadosExternos();
    enviarDadosParaSerial();
} // FIM LOOP
>>>>>>> luiz/master

void ligarEletronicos(){
  digitalWrite( atuadorRele, HIGH );
  irSend.sendNEC( comandoDeLigarArCondicionado, 32);
  estadoAr = true;
  jaEnviouLigar = true;
}

void desligarEletronicos(){
  digitalWrite( atuadorRele, LOW );
  irSend.sendNEC(comandoDeDesligarArCondicionado, 32);
  estadoAr = false;
  jaEnviouDesligar = true;
}

String receberDadosSerial(){
  if(Serial.available() > 0){
    serialReturn = Serial.readString();
    return serialReturn;
  }
    return "";
}
void receberDadosSensores(){
  statusSensorPIR = digitalRead( sensorPIR );
  statusSensorTemperatura = analogRead( sensorTemperatura );
}

void receberTodosOsDadosExternos(){
    receberDadosSensores();
    receberDadosSerial();
}

bool isPresencaOn(){
    if(statusSensorPIR == 1){
        return true;
    }
    return false;
}

void enviarDadosParaSerial(){
    Serial.print(statusSensorPIR+"|"+statusSensorTemperatura);
 }

