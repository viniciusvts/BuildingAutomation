using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using System.IO.Ports;
using BuildingAutomation.Models;
using System.Linq;

namespace BuildingAutomation.Controllers
{
    public class TaskEventsToArduino
    {
        private List<Task> tasks;
        AppContext dbContext;
        SerialPort serialPort;
        String PORT = "COM3";
        int BAUD = 9600;

        // construtor singleton
        private TaskEventsToArduino()
        {
            tasks = new List<Task>();
            dbContext = new AppContext();
            //comunicação com arduino
            serialPort = new SerialPort();
            serialPort.PortName = PORT;
            serialPort.BaudRate = BAUD;
            serialPort.ReadTimeout = 200;
            serialPort.WriteTimeout = 200;
            setAllTasks();
        }
        private static TaskEventsToArduino _singleton;
        public static TaskEventsToArduino getInstance
        {
            get
            {
                if (_singleton == null)
                {
                    _singleton = new TaskEventsToArduino();
                }
                return _singleton;
            }
        }

        //manipula as tasks
        private void setAllTasks()
        {
            DateTime now = new DateTime();
            this.killAllTasks();
            IEnumerable<Event> toOn = this.dbContext.Events
                .Where(q => q.Start > now)
                .OrderBy(q => q.Start)
                .AsEnumerable<Event>();
            var toOff = this.dbContext.Events
                .Where(q => q.End > now)
                .OrderBy(q => q.End)
                .AsEnumerable<Event>();
            System.Diagnostics.Debug.WriteLine("toOn", toOn);
            System.Diagnostics.Debug.WriteLine("toOff", toOff);
        }
        private TimeSpan getIntervalTo(DateTime futureDate)
        {
            DateTime now = new DateTime();
            return futureDate - now;
        }
        private void addTask(int id, DateTime futureDate, bool isToSetOn)
        {
            Action<object> action = (object obj) =>
            {
                Thread.Sleep(getIntervalTo(futureDate));
                if (isToSetOn) this.ArduinoOnAll();
                else this.ArduinoOffAll();
            };
            //Task.Run( () => Thread.Sleep(2000));
            tasks.Add(new Task(action, id));
        }
        private void killAllTasks()
        {
            foreach (Task task in tasks)
            {
                // task.comando para matar
            }
            tasks.Clear();
        }



        // Comunicação com o arduino
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
            bool _status = false;
            bool _pre = false;
            int _temp = -1;
            if (data.Length == 3)
            {
                _status = data[0] == "1";
                _pre = data[1] == "1";
                _temp = Int32.Parse(data[2]);
            }
            return new Arduino(_status, _pre, _temp);
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
