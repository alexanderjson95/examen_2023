import { CustomButton } from '../comps/CustomButton'
import { CustomForm } from '../comps/CustomForm'
import { CustomInput } from '../comps/CustomInput'
import { Authenticate } from '../api/userApi';
import type { User } from '../models/User';
import React, { useState } from 'react';

    export const LoginScreen = () => {

        const [user, setUser] = useState<User | null>(null);
        const [username, setUsername] = useState("")
        const [password, setPassword] = useState("")
        const [error, setError] = useState("")



        const Auth = (e: React.FormEvent<HTMLFormElement>) => {
            e.preventDefault();
            Authenticate(username, password).then((data) => {
                setUser(data);
                console.log(data?.fName)
            })
            .catch((e) => {
                console.error('fail',e);
                setError("Error on login")
            })
        }

      return (
        <>

        <CustomForm onSubmit={Auth}>
                <>
                <p>{user?.fName || error}</p>
                    <CustomInput 
                        onChange={(e) => setUsername(e.target.value)}  
                        value={username} 
                        required={true}
                    />
                    <CustomInput 
                        onChange={(e) => setPassword(e.target.value)}  
                        value={password} 
                        required={true}
                    />
                    <CustomButton
                        label="Log In" 
                        type='submit' 
                        disabled={false}
                    />

                    <CustomButton
                        label="Register" 
                        type='submit' 
                        disabled={false}
                    />
                </>
            </CustomForm>  
        </>
      )
    }
    
    export default LoginScreen
    
