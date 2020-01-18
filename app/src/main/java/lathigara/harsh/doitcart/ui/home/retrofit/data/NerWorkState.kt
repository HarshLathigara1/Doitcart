package lathigara.harsh.doitcart.ui.home.retrofit.data


enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    END_OF_LIST

}


// step 1
// step 2 get popjo class avengers
//step 3 configure retrofit // movieDb interface
// step 4 creare retrofit client TheMovieDbClient
// step 5 fetcing data with the help of rx java
//step 6 composite dispodable to destrroy  coming stream of data
//  step 7 then we are adding repository
//step 8 put all thhings in viewmodel
// then adding all thing in activity by calling Viewmodel
class NetWorkState(val status: Status,val msg:String){

    companion object{
        val LOADED:NetWorkState = NetWorkState(Status.SUCCESS,"success")
        val LOADING:NetWorkState =  NetWorkState(Status.RUNNING,"running")
        val ERROR:NetWorkState =  NetWorkState(Status.FAILED,"error")
        val END_OF_LIST:NetWorkState = NetWorkState(Status.END_OF_LIST,"NO PAGES LEFT")
    }



}