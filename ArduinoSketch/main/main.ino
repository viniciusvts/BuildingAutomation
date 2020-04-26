/*
  BuildingAutomation
  Aplicação com o proposito de coordenar ativação e desativação
  automática de eletro-eletronicos em predios comerciais
*/

#include "IRremote.h" //biblioteca do controle remoto

//Comunicacao wifi
#include "ESP8266.h"
#define SSID        "@vtsanatana"
#define PASSWORD    "impossivelesquecer2"
#define BUFFERINDEXS    512
//Cria objeto de conexão wifi com o módulo
SoftwareSerial esp(10,11);
ESP8266 wifi(esp);
//Variavel para buffer de dados de trasmissao
uint8_t buffer[BUFFERINDEXS] = {0};
//Como usamos multiplas conexoes, cada conexao tem sua ID, e precisa ser armazenada para referencia no programa. Usamos    //essa variavel para isso.
uint8_t mux_id;

/**
 * debug no console
 */
const boolean debugNoConsole = true;
//setar os sensores
const int sensorPIR = 7;
const int sensorTemperatura = A4;

//setar os atuadores
const int atuadorRele = 6;
const int atuadorControleInfravermelho = 4;//porta padrao do IRsend
IRsend irSend;

//constantes utilizadas
const unsigned long comandoDeLigarArCondicionado = 0xBD08F7; //abaixar volume
const unsigned long comandoDeDesligarArCondicionado = 0xBD30CF;//aumentar volume
const int bitsComandoIR = 32; // valor em bits do comando infravermelho
const char comandocharParaLigarTudo = 'l';
const char comandocharParaDesligarTudo = 'd';
const char comandocharParaEnviarTemperatura = 't';
const char comandocharParaEnviarPresenca = 'p';


//setar variaveis de controle
bool estadoAr = false; // Controle de estado do ar condicionado
bool jaEnviouLigar = false;
bool jaEnviouDesligar = false;
char serialReturn;
int statusSensorPIR = 0;
int statusSensorTemperatura = 0;
int statusSensorTemperaturaUltimoValorLido = 0;
const unsigned long intervaloDeVerificacaoDoArCondicionado = 60000;
unsigned long millisUltimoValorLido = 0;

//funcões utilizadas no programa

void ligarEletronicos(){
  digitalWrite( atuadorRele, HIGH );
  for( int i =0 ; i <3 ; i++){
    irSend.sendRC5( comandoDeLigarArCondicionado, bitsComandoIR);
    delay(50);
  }
  jaEnviouLigar = true;
}

void desligarEletronicos(){
  digitalWrite( atuadorRele, LOW );
  for( int i =0 ; i <3 ; i++){
    irSend.sendRC5(comandoDeDesligarArCondicionado, bitsComandoIR);
    delay(50);
  }
  jaEnviouDesligar = true;
}

char receberDados(){
  if(Serial.available() > 0){
    serialReturn = Serial.read();
    return serialReturn;
  }
    return 0;
}
void receberDadosSensores(){
  statusSensorPIR = digitalRead( sensorPIR );
  // Temperatura =  [(Valor lido em A0)*(5/1023)]/10mV - https://portal.vidadesilicio.com.br/lm35-medindo-temperatura-com-arduino/
  statusSensorTemperatura = ( float(analogRead(sensorTemperatura) ) * 5 / (1023) ) / 0.01;
}

void receberTodosOsDadosExternos(){
    receberDadosSensores();
    receberDados();
}

bool isPresencaOn(){
    if(statusSensorPIR == 1){
        return true;
    }
    return false;
}

void enviarTemperatura(){
    sendIntToWifi(mux_id, 33);
}

void enviarPresenca(){
    sendIntToWifi(mux_id, statusSensorPIR);
 }


void startESPConection(){
    Serial.println("Iniciando ESP.");
    
    Serial.print("Versao de Firmware ESP8266: ");
    //A funcao wifi.getVersion() retorna a versao de firmware informada pelo modulo no inicio da comunicacao
    Serial.println(wifi.getVersion().c_str());
    
    //Agora vamos habiliar a funcionalidade MUX, que permite a realizacao de varias conexoes TCP/UDP
    if (wifi.enableMUX()) {
        Serial.println("Multiplas conexoes OK.");
    } else {
        Serial.println("Erro ao setar multiplas conexoes.");
    }
 
    //Agora vamos conectar no ponto de WiFi informado no inicio do codigo, e ver se corre tudo certo
    if (wifi.joinAP(SSID, PASSWORD)) {
        Serial.println("Conectado com Sucesso.");
        Serial.println("IP: ");
        //rotina wifi.getLocalIP() retorna o IP usado na conexao com AP conectada.
        Serial.println(wifi.getLocalIP().c_str());    
    } else {
        Serial.println("Falha na conexao AP.");
    }
    
    //Inicia servidor TCP na porta 80 (veja depois a funcao "startServer(numero_porta)", que serve para UDP!
    if (wifi.startTCPServer(80)) {
        Serial.println("Servidor iniciado com sucesso.");
    } else {
        Serial.println("Erro ao iniciar servidor.");
    }    
    Serial.println("Setup finalizado!");
}

/**
 * verifica o buffer atras da url atribuindo a commandoRecebido o primeiro caractere após a /
 * o objetivo e utiliza esse caracter como comando para o restante do programa,
 * não esta dentro do escopo avaliar urls com mais de um caracter após a  /
 * @author Vinicius de Santana
 */
boolean pegaComandoDaUrl(char* commandoRecebido, uint8_t buffer[BUFFERINDEXS]){
  for(uint8_t i = 0; i < BUFFERINDEXS; i++) {
    if((char)buffer[i] == '/'){
      *commandoRecebido = buffer[i+1];
      if(*commandoRecebido != ' '){
        return true;
      }else{
        return false;
      }
    }
  }
  return false;
}

boolean sendCharToWifi(uint8_t mux_id, const char* charToSend){
    uint8_t buffer[1] = {*charToSend};
    //wifi.send, veja abaixo:
    if( wifi.send( mux_id, buffer, sizeof(buffer) ) && debugNoConsole ) {
        Serial.print("Enviado de volta: ");
        Serial.print(*charToSend);
        Serial.print("\r\n");
        return true;
    } else if(debugNoConsole) {
        Serial.print("Erro ao enviar de volta\r\n");
    }
    return false;
}

boolean sendIntToWifi(uint8_t mux_id, const int* intToSend){
    // String data = ""+*intToSend;
    char* data = ""+*intToSend;
    return sendCharToWifi(mux_id, data);
}

//FIM DAS FUNÇÔES
////////////////////////////////////////////////////////////////////////////////////

void setup() {
    Serial.begin(9600); //comunicação Serial
    millis(); // Duração de 50 dias
    //sensores
    pinMode( sensorPIR, INPUT );
    //o sensorTemperatura é analogico
    //wifi
    startESPConection();
    //atuadores
    pinMode( atuadorRele, OUTPUT );
    pinMode( atuadorControleInfravermelho, OUTPUT );
}

//////////////////////////////////////////////////////////////////////////////////

void loop() {

    receberTodosOsDadosExternos();
    //E esta variavel len serve para armazenar o comprimento de dados recebidos por meio da rotina wifi.recv(), que tambem     //associa ao buffer os dados recebidos e ao mux_id a id responsavel pela transmissao
    uint32_t len = wifi.recv(&mux_id, buffer, sizeof(buffer), 1000);
    if (len > 0) { 
        char serialReturn;
        pegaComandoDaUrl(&serialReturn, buffer);
        if(debugNoConsole){
          Serial.print("Recebido de :");
          Serial.println(mux_id);
          Serial.print("[");
          Serial.print(serialReturn);
          Serial.println("]");
        }
        // verifica qual comando de char foi recebida
        if ( serialReturn == comandocharParaEnviarTemperatura ){
            enviarTemperatura();
            serialReturn = 0;
        }else if( serialReturn == comandocharParaEnviarPresenca ){
            enviarPresenca();
            serialReturn = 0;
        }else if ( serialReturn == comandocharParaLigarTudo ){
            //se no loop anterior recebi comandocharParaDesligarTudo
            jaEnviouDesligar = false;
            serialReturn = 0;
            if(estadoAr == false){
                ligarEletronicos(); //liga a flag "jaEnviouLigar"
                statusSensorTemperaturaUltimoValorLido = statusSensorTemperatura; //salvo temper atual
                millisUltimoValorLido = millis();
            }else{
                digitalWrite( atuadorRele, HIGH);
            }
        }else if( serialReturn == comandocharParaDesligarTudo ){
            //se no loop anterior recebi comandocharParaLigarTudo
            jaEnviouLigar = false;
            serialReturn = 0;
            if( isPresencaOn() ){
                //não faz nada
                jaEnviouDesligar = true; // para entrar na condicional
                /* se tem alguem presente, nao vai atuar em nada mas,
                precisa ter parametro de temperatura para qundo entrar na condicional
                da flag jaEnviouDesligar, se não tiver esse parametro, nunca vai desligar*/
                statusSensorTemperaturaUltimoValorLido = statusSensorTemperatura; 
            } else if(estadoAr){
                desligarEletronicos(); //liga a flag "jaEnviouDesligar"
                statusSensorTemperaturaUltimoValorLido = statusSensorTemperatura; //slvo temper atual
                millisUltimoValorLido = millis();
            }else{
                digitalWrite( atuadorRele, LOW);
            }
        }
        if (wifi.releaseTCP(mux_id)) {
            if (debugNoConsole){
                Serial.print("Liberando conexao TCP com ID: ");
                Serial.print(mux_id);
                Serial.println(" OK");
            }
        } else {
            if (debugNoConsole){
                Serial.print("Erro ao liberar TCP com ID: ");
                Serial.print(mux_id);
            }
        }
        if (debugNoConsole){
            Serial.print("Status:[");
            Serial.print(wifi.getIPStatus().c_str());
            Serial.println("]");
        }
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
    }else if( jaEnviouDesligar ){
        if( isPresencaOn() ){
            // não faz nada
        } else if( ( millis() - millisUltimoValorLido)  >= intervaloDeVerificacaoDoArCondicionado){
            //se não desligou mando o comando de novo
            if(statusSensorTemperatura <= statusSensorTemperaturaUltimoValorLido){
                desligarEletronicos();
                millisUltimoValorLido  = millis(); // Verificar temp de 5 em 5 minutos.
                // se desligou desativo as flags
            }else{
                jaEnviouDesligar = false; //para não entrar mais na condicional
                estadoAr = false; //REALMENTE desligou
            }
        } else{
            digitalWrite( atuadorRele, LOW);
        }
    }
} // FIM LOOP
