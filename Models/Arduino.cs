namespace BuildingAutomation.Models
{
    public class Arduino
    {
        public bool status { get; set; }
        public bool pre { get; set; }
        public int temp { get; set; }
        public Arduino(bool _status, bool _pre, int _temp)
        {
            status = _status;
            pre = _pre;
            temp = _temp;
        }
    }
}
