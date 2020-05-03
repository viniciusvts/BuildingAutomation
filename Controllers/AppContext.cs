using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Google.Protobuf.WellKnownTypes;
using Microsoft.EntityFrameworkCore;

namespace BuildingAutomation.Controllers
{
    public class AppContext : DbContext
    {
        public DbSet<Models.Event> Events { get; set; }
        public AppContext(DbContextOptions<AppContext> options)
            : base(options)
        {
        }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseMySQL("server=localhost;database=buildingauto;user=root;password=root");
            }
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        { }
    }
}
