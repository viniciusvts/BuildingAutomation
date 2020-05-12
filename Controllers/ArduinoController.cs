using BuildingAutomation.Models;
using Microsoft.AspNetCore.Mvc;
using System;

namespace BuildingAutomation.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ArduinoController : ControllerBase
    {
        TaskEventsToArduino aTask;
        public ArduinoController()
        {
            aTask = TaskEventsToArduino.getInstance;
        }
        // GET: api/Arduino
        [HttpGet("get")]
        public Arduino Get() => aTask.ArduinoGetData();

        [HttpGet("onall")]
        public bool OnAll()
        {
            return aTask.ArduinoOnAll();
        }

        [HttpGet("offall")]
        public bool OffAll()
        {
            return aTask.ArduinoOffAll();
        }
    }
}
