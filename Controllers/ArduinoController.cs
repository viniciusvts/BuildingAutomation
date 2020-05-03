using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BuildingAutomation.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
// conexão serial
using System.IO.Ports;

namespace BuildingAutomation.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ArduinoController : ControllerBase
    {
        SerialPort serialPort;
        String PORT = "COM3";
        int BAUD = 9600;
        public ArduinoController()
        {
            serialPort = new SerialPort();
            serialPort.PortName = PORT;
            serialPort.BaudRate = BAUD;
            serialPort.ReadTimeout = 200;
            serialPort.WriteTimeout = 200;
        }
        // GET: api/Arduino
        [HttpGet("get")]
        public Arduino Get(String comand)
        {
            return ArduinoGetData();
        }

        [HttpGet("onall")]
        public bool OnAll(String comand)
        {
            return ArduinoOnAll();
        }

        [HttpGet("offall")]
        public bool OffAll(String comand)
        {
            return ArduinoOffAll();
        }

        /**
         * Recupera os dados do arduino pela comunicação serial
         */
        public Arduino ArduinoGetData()
        {
            if (!serialPort.IsOpen)
            {
                try
                {
                    serialPort.Open();
                }
                catch { }
            }
            String serialData = "";
            try
            {
                serialPort.Write("r");
                serialData = serialPort.ReadLine();
                serialPort.Close();
            }
            catch { }
            String[] data = serialData.Split('&');
            // defino uns valores padrões em caso de erro
            bool _pre = false;
            int _temp = -1;
            if (data.Length == 2)
            {
                _pre = data[0] == "1";
                _temp = Int32.Parse(data[1]);
            }
            return new Arduino(_pre, _temp);
        }

        /**
         * Envia ligar tudo pela porta serial
         */
        public bool ArduinoOnAll()
        {
            if (!serialPort.IsOpen)
            {
                try
                {
                    serialPort.Open();
                }
                catch
                {
                    return false;
                }
            }
            try
            {
                serialPort.Write("l");
                serialPort.Close();
            }
            catch
            {
                return false;
            }
            return true;
        }

        /**
         * Envia desligar tudo pela porta serial
         */
        public bool ArduinoOffAll()
        {
            if (!serialPort.IsOpen)
            {
                try
                {
                    serialPort.Open();
                }
                catch
                {
                    return false;
                }
            }
            try
            {
                serialPort.Write("d");
                serialPort.Close();
            }
            catch
            {
                return false;
            }
            return true;
        }
    }
}
