﻿namespace BuildingAutomation.Models
{
    public class Arduino
    {
        public bool pre { get; set; }
        public int temp { get; set; }
        public Arduino(bool _pre, int _temp)
        {
            pre = _pre;
            temp = _temp;
        }
    }
}