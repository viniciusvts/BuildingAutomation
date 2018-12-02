/*
  BuildingAutomation
  Aplicação com o proposito de coordenar ativação e desativação
  automática de eletro-eletronicos em predios comerciais
*/

#include "IRremote.h" //biblioteca do controle remoto

//setar os sensores
const int sensorPIR = 3;
const int sensorTemperatura = A4;

//setar os atuadores
const int atuadorRele = 6;
const int atuadorControleInfravermelho = 7;
IRsend irSend;

//constantes utilizadas
const unsigned long comandoDeLigarArCondicionado = 0x39C600FF; //exemplo: 0x39C600FF
const unsigned long comandoDeDesligarArCondicionado = 0x39C600FF;
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
const unsigned long intervaloDeVerificacaoDoArCondicionado = 300000;
unsigned long millisUltimoValorLido;

//funcões utilizadas no programa

void ligarEletronicos(){
  digitalWrite( atuadorRele, HIGH );
  irSend.sendNEC( comandoDeLigarArCondicionado, 32);
  jaEnviouLigar = true;
}

void desligarEletronicos(){
  digitalWrite( atuadorRele, LOW );
  irSend.sendNEC(comandoDeDesligarArCondicionado, 32);
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
  // Temperatura =  [(Valor lido em A0)*(5/1023)]/10mV - https://portal.vidadesilicio.com.br/lm35-medindo-temperatura-com-arduino/
  statusSensorTemperatura = ( float(analogRead(sensorTemperatura) ) * 5 / (1023) ) / 0.01;
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
    Serial.print(statusSensorPIR);
    Serial.print("|");
    Serial.println(statusSensorTemperatura);
 }

//FIM DAS FUNÇÔES
////////////////////////////////////////////////////////////////////////////////////

void setup() {
    Serial.begin(9600); //comunicação Serial
    millis(); // Duração de 50 dias
    //sensores
    pinMode( sensorPIR, INPUT );
    //o sensorTemperatura é analogico

    //atuadores
    pinMode( atuadorRele, OUTPUT );
    pinMode( atuadorControleInfravermelho, OUTPUT );
}

//////////////////////////////////////////////////////////////////////////////////

void loop() {

    receberTodosOsDadosExternos();
    enviarDadosParaSerial();

    if( serialReturn.equals(comandoStringParaLigarTudo) ){
        //se no loop anterior recebi comandoStringParaDesligarTudo
        jaEnviouDesligar = false;
        if(estadoAr == false){
            ligarEletronicos(); //liga a flag "jaEnviouLigar"
            statusSensorTemperaturaUltimoValorLido = statusSensorTemperatura; //salvo temper atual
            millisUltimoValorLido = millis();
        }else{
            digitalWrite( atuadorRele, HIGH);
        }
        if( jaEnviouLigar ){
            if( ( millis() - millisUltimoValorLido ) >= intervaloDeVerificacaoDoArCondicionado){
                // se não ligou mando o comando de novo
                if(statusSensorTemperatura >= statusSensorTemperaturaUltimoValorLido){
                    ligarEletronicos();
                    millisUltimoValorLido  = millis(); // Verificar temp em 5 minutos.
                // se ligou desativo as flags
                }else{
                    jaEnviouLigar = false; //para não entrar mais na condicional
                    estadoAr = true; //REALMENTE ligou o ar
                }
            }
        }
    }else if( serialReturn.equals(comandoStringParaDesligarTudo) ){
        //se no loop anterior recebi comandoStringParaLigarTudo
        jaEnviouLigar = false;
        if(estadoAr){
            desligarEletronicos(); //liga a flag "jaEnviouDesligar"
            statusSensorTemperaturaUltimoValorLido = statusSensorTemperatura; //slvo temper atual
            millisUltimoValorLido = millis();
        }else{
            digitalWrite( atuadorRele, LOW);
        }
        if( jaEnviouDesligar ){
            if( ( millis() - millisUltimoValorLido)  >= intervaloDeVerificacaoDoArCondicionado){
                //se não desligou mando o comando de novo
                if(statusSensorTemperatura <= statusSensorTemperaturaUltimoValorLido){
                    desligarEletronicos();
                    millisUltimoValorLido  = millis(); // Verificar temp de 5 em 5 minutos.
                    // se desligou desativo as flags
                }else{
                    jaEnviouDesligar = false; //para não entrar mais na condicional
                    estadoAr = false; //REALMENTE desligou
                }
            }
        }
    }
} // FIM LOOP
