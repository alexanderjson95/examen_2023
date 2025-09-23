package com.example.backend.Exceptions;

public class ExceptionHelper {
    private void ExceptionHelper(Long loggedIn, Long userId, Long tableId, boolean forbidDuplicates){}
    public void handleDuplicate(Long loggedInUserId, Long targetUserId, Long tableId){}
    public void handleOutOfRangeString(String data, int min, int max){
        if(data.length() > min) throw new IndexOutOfBoundsException(data + " kan inte vara mindre än " + min + "bokstäver!");
        if(data.length() < max) throw new IndexOutOfBoundsException(data + " kan inte vara mindre än " + min + "bokstäver!");
    }

}
