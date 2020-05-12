using Microsoft.EntityFrameworkCore;

namespace BuildingAutomation.Controllers
{
    public class AppContext : DbContext
    {
        public DbSet<Models.Event> Events { get; set; }
        public AppContext(DbContextOptions<AppContext> options) : base(options) { }
        public AppContext() { }
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
