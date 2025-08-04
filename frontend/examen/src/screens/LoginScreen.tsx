import { CustomButton } from '../comps/CustomButton'
import { CustomForm } from '../comps/CustomForm'
import { CustomInput } from '../comps/CustomInput'
import { getProjectsByUserId, Authenticate } from '../api/userApi';
import type { User } from '../models/User';
import React, { useState } from 'react';
import type { Project } from '../models/Project';

    export const LoginScreen = () => {

        const [user, setUser] = useState<User| null>(null);
        const [projects, setProject] = useState<Project[]>([]);
        const [username, setUsername] = useState("")
        const [password, setPassword] = useState("")
        const [error, setError] = useState("")

        const getProjects = () => {
            console.log(user?.uid)
            getProjectsByUserId(user?.uid || 0) 
            .then((pdata) => {
                setProject(pdata);
            })
            .catch((e) => {
                console.error('fail',e);
                setError("Error on login" + e)
            })
        }

        const Auth = (e: React.FormEvent<HTMLFormElement>) => {
            e.preventDefault();
            Authenticate(username, password).then((data) => {
                setUser(data);
                console.log(data?.fName)
                console.log(data?.uid)
            })
            .catch((e) => {
                console.error('fail',e);
                setError("Error on login" + e)
            })
        }

      return (
        <>

        <CustomForm onSubmit={Auth}>
                <>
                <p>{user?.fName || error}</p>
                {projects?.map((project) => (
                    <p>{project.projectName}</p>
                )) || error}

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
                    <CustomButton
                        label="Projects" 
                        type='button' 
                        disabled={false}
                        onClick={getProjects}
                    />
        </>
      )
    }
    
    export default LoginScreen
    
