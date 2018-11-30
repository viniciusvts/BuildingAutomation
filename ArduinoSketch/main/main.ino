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
int statusSensorTemperaturaUltimoValorLido = 0;
const unsigned long intervaloDeVerificaçãoDoArCondicionado = 300000;
unsigned long millisUltimoValorLido;

void setup() {
    Serial.begin(9600); //comunicação Serial
    millis(); // Duração de 50 dias
    //sensores
    pinMode( sensorPIR, INPUT );
    //o sensorTemperatura é analogico
  
    //atuadores
    pinMode( atuadorRele, OUTPUT );
    pinMode( atuadorControleInfravermelho, OUTPUT );
    receberTodosOsDadosExternos(); 
}

void loop() { 
    
    receberTodosOsDadosExternos();
    enviarDadosParaSerial();
    
    if(serialReturn.equals(comandoStringParaLigarTudo) || jaEnviouLigar){
        // ligarEletronicos(); // Verificar 
        if(estadoAr == false){
            ligarEletronicos();
            statusSensorTemperaturaUltimoValorLido = statusSensorTemperatura; //salvo temp atual
            millisUltimoValorLido = millis();
        }else if( jaEnviouLigar ){
            if( ( millis() - millisUltimoValorLido ) >= intervaloDeVerificaçãoDoArCondicionado){ 
                if(statusSensorTemperatura >= statusSensorTemperaturaUltimoValorLido){
                    ligarEletronicos();
                    millisUltimoValorLido  = millis(); // Verificar temp em 5 minutos.
                }else{ //se reconheço  que REALMENTE ligou o ar
                    jaEnviouLigar = false; //para não estrar mais na condicional
                }
            }
        }
    }
    }else if(serialReturn.equals(comandoStringParaDesligarTudo) || jaEnviouDesligar){
        if(estadoAr == true){      
            desligarEletronicos();
        }else if(estadoAr == false){
            if(millis() - millisUltimoValorLido  >= intervaloDeVerificaçãoDoArCondicionado){ 
                    if(statusSensorTemperatura < 23){
                      desligarEletronicos();
                      millisUltimoValorLido  = millis(); // Verificar temp de 5 em 5 minutos.
                    }
                                       
             }
        }
    }
} // FIM LOOP

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

