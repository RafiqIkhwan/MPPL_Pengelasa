package com.example.rpl_pengelasan.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.rpl_pengelasan.DateConverter
import com.example.rpl_pengelasan.data.dao.AnggotaDao
import com.example.rpl_pengelasan.data.dao.CustomerDao
import com.example.rpl_pengelasan.data.dao.PesananDao
import com.example.rpl_pengelasan.data.dao.TeamDao
import com.example.rpl_pengelasan.data.dao.UserDao
import com.example.rpl_pengelasan.data.entity.Anggota
import com.example.rpl_pengelasan.data.entity.Customer
import com.example.rpl_pengelasan.data.entity.Pesanan
import com.example.rpl_pengelasan.data.entity.Team
import com.example.rpl_pengelasan.data.entity.User


@Database(
    entities = [
        User::class,
        Anggota::class,
        Customer::class,
        Pesanan::class,
        Team::class
               ],
    version = 1
)
@TypeConverters(
    DateConverter::class
)
abstract class RoomDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun anggotaDao(): AnggotaDao
    abstract fun customerDao(): CustomerDao
    abstract fun pesananDao(): PesananDao
    abstract fun teamDao(): TeamDao

    // coba2 nurut
    companion object{
        @Volatile private var instance: RoomDB? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: BuildDatabase(context).also { instance = it }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Migrasi dari versi 1 ke versi 2
                // Contoh: Membuat tabel pesanan dengan foreign key
                database.execSQL("CREATE TABLE IF NOT EXISTS `pesanan` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `pelangganId` INTEGER NOT NULL, `total` REAL NOT NULL, FOREIGN KEY(`pelangganId`) REFERENCES `customer`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE)")
            }
        }


        private fun BuildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RoomDB::class.java,
            "pengelasan.db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()

        fun getInstance(context: Context): RoomDB {
            return instance ?: synchronized(this) {
                val instance2 = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "pengelasan.db"
                ).build()
                instance = instance2
                instance2
            }
        }

    }


}
