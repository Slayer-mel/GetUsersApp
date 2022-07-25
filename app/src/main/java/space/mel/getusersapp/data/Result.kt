package space.mel.getusersapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "user_table")
data class Results(
    @PrimaryKey(autoGenerate = false)
    val entityId: Int = 0,
    @SerializedName("results")
    val results: List<Result>
) : Parcelable

@Parcelize
@Entity
data class Result(
    val gender: String?,
    val name: Name?,
    val location: Location?,
    val email: String?,
    val login: Login?,
    val dob: Dob?,
    val registered: Dob?,
    val phone: String?,
    val cell: String?,
    val id: ID?,
    val picture: Picture?,
    val nat: String?
) : Parcelable, Serializable

@Parcelize
@Entity
data class Dob(
    val date: String?,
    val age: Long?
) : Parcelable

@Parcelize
@Entity
data class ID(
    val name: String?,
    val value: String?
) : Parcelable

@Parcelize
@Entity
data class Location(
    val street: Street?,
    val city: String?,
    val state: String?,
    val country: String?,
    val postcode: String?,
    val coordinates: Coordinates?,
    val timezone: Timezone?
) : Parcelable

@Parcelize
@Entity
data class Coordinates(
    val latitude: String?,
    val longitude: String?
) : Parcelable

@Parcelize
@Entity
data class Street(
    val number: String?,
    val name: String?
) : Parcelable

@Parcelize
@Entity
data class Timezone(
    val offset: String,
    val description: String
) : Parcelable

@Parcelize
@Entity
data class Login(
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
) : Parcelable

@Parcelize
@Entity
data class Name(
    val title: String?,
    val first: String?,
    val last: String?
) : Parcelable

@Parcelize
@Entity
data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
) : Parcelable

class ResultConverters() {

    @TypeConverter
    fun toResultList(value: String): List<Result> {
        val type = object : TypeToken<List<Result>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toString(list : List<Result>) : String {
        return Gson().toJson(list)
    }
}

class NameConverter() {
    @TypeConverter
    fun toName(value: String): Name {
        val type = object : TypeToken<Name>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toString(value : Name) : String {
        return Gson().toJson(value)
    }
}

class LocationConverter() {
    @TypeConverter
    fun toLocation(value: String): Location {
        val type = object : TypeToken<Location>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toString(name : Location) : String {
        return Gson().toJson(name)
    }
}

class LoginConverter() {
    @TypeConverter
    fun toLogin(value: String): Login {
        val type = object : TypeToken<Login>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toString(value : Login) : String {
        return Gson().toJson(value)
    }
}

class DobConverter() {
    @TypeConverter
    fun toDob(value: String): Dob {
        val type = object : TypeToken<Dob>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toString(value : Dob) : String {
        return Gson().toJson(value)
    }
}

class IDConverter() {
    @TypeConverter
    fun toID(value: String): ID {
        val type = object : TypeToken<ID>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toString(value : ID) : String {
        return Gson().toJson(value)
    }
}

class PictureConverter() {
    @TypeConverter
    fun toPicture(value: String): Picture {
        val type = object : TypeToken<Picture>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toString(value : Picture) : String {
        return Gson().toJson(value)
    }
}
