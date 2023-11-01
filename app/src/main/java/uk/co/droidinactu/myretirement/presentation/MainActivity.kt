/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package uk.co.droidinactu.myretirement.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import uk.co.droidinactu.myretirement.R
import uk.co.droidinactu.myretirement.presentation.theme.MyRetirementTheme
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp("Android")
        }
    }
}

@Composable
fun WearApp(greetingName: String) {
    MyRetirementTheme {
        /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
         * version of LazyColumn for wear devices with some added features. For more information,
         * see d.android.com/wear/compose.
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center,
        ) {
            RetireIn(integerResource(id = R.integer.retirement_age))
        }
    }
}

@Composable
fun RetireIn(retireAge: Int) {
    // TODO: Calculate how long until retirement

    val bday = LocalDate.of(
        integerResource(id = R.integer.dob_year),
        integerResource(R.integer.dob_month),
        integerResource(R.integer.dob_day),
    )
    val retireDate = bday.plusYears(retireAge.toLong())
    val today = LocalDate.now()

    val age = Period.between(today, retireDate)
    val years = age.years
    val months = age.months
    val days = age.days

    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 22.sp,
        color = MaterialTheme.colors.primary,
        text = stringResource(
            R.string.retire_in,
            years,
            months,
            days,
            retireAge,
            retireDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
        ),
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp("Preview Android")
}
