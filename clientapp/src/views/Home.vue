<template>
  <section class="home">
    <h1>Building Automation</h1>
    <div class="container">
      <!--card1 -->
      <div class="card row">
        <div class="col">
          <h2>Sala 1</h2>
          <p class="sensorStatus" :class="{'active':arduino.status}">Aparelhos ligados: </p>
          <p>Temperatura: {{arduino.temp}}°</p>
          <p class="sensorStatus" :class="{'active':arduino.pre}">Sensor Presença: </p>
          <button @click="sendPower" :class="{'active':arduino.status}">{{getTogleName}}</button>
        </div>
        <div class="col">
          <h4>Cadastrar Novo Horário</h4>
          <form action="#_">
            <input
            type="text" name="nome" id="nome"
            placeholder="Nome do Evento" v-model="formData.name">
            <input
            type="datetime-local" name="start" id="start"
            placeholder="Início do Evento" v-model="formData.start">
            <input type="datetime-local" name="end" id="end"
            placeholder="Final do Evento" v-model="formData.end">
            <button @click.prevent="sendForm">
              Enviar
            </button>
          </form>
        </div>
      </div>
      <!--card1 -->
      <div class="card row">
        <div class="col">
          <h2>Sala 2 (desativado)</h2>
          <p class="sensorStatus">Aparelhos ligados: </p>
          <p>Temperatura: 00°</p>
          <p class="sensorStatus">Sensor Presença: </p>
          <button disabled>Desativado</button>
        </div>
        <div class="col">
          <h4>Cadastrar Novo Horário</h4>
          <form action="#_">
            <input
            type="datetime-local" name="start" id="start"
            placeholder="Início do Evento">
            <input type="datetime-local" name="end" id="end"
            placeholder="Final do Evento">
            <button disabled>Desativado</button>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      getInterval: null,
      arduino: {
        temp: 32,
        pre: false,
        status: true
      },
      formData: {
        name: null,
        start: null,
        end: null
      }
    }
  },
  mounted() {
    // this.getInterval = setInterval(this.getArduinoData, 2000);
  },
  beforeDestroy: () =>{
    if(this.getInterval != null){
      clearInterval(this.getInterval);
    }
    
  },
  methods: {
    sendPower(){
      if(this.arduino.status){
        this.sendOff();
      }else{
        this.sendOn();
      }
    },
    getArduinoData(){
      fetch('/api/arduino/get', { method: "GET" })
      .then(response => {
        if(response.ok)
          return response.json();
      })
      .then(json =>{
        this.arduino = json;
      });
    },
    sendOn(){
      fetch('/api/arduino/onall', { method: "GET" });
    },
    sendOff(){
      fetch('/api/arduino/offall', { method: "GET" });
    },
    sendForm(){
      let header = new Headers();
      header.append("Content-Type", "application/json");
      fetch("/api/events", {
        method: "POST",
        body: JSON.stringify(this.formData),
        headers: header
      })
      .then(response => {
        return response.json();
      })
      .then(json => {
        console.log(json);
      });
    }
  },
  computed:{
    getTogleName(){
      if(this.arduino.status)
        return 'Desligar Aparelhos';
      else
        return 'Ligar Aparelhos';
    }
  }
}
</script>
