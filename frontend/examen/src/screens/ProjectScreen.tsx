import type { Project } from '../models/Project';
import { getProjectsByUserId } from '../api/userApi';
import React, { useState } from 'react';
import type { User } from '../models/User';
import { CustomList } from '../comps/CustomList';


export const ProjectScreen = () => { 
    const [user, setUser] = useState<User| null>(null); //cookie functiom att hämta
    const [projects, setProject] = useState<Project[]>([]);
    const [uProject, setUProject] = useState<Project | null>(null);
    const [error, setError] = useState("")

    const getProjects = () => {

            console.log(user?.uid)
            //getProjectsByUserId(user?.uid || 0) 
            getProjectsByUserId(10) 
            .then((pdata) => {
                setProject(pdata);
                console.log(projects)
            })
            .catch((e) => {
                console.error('fail',e);
                setError("Error on login" + e)
            })

                {projects?.map((project) => (
                setUProject(project)
                )) || error}
                console.log(uProject)

        }
    
    React.useEffect(() => {
        getProjects();
    }, []);
    return (
        <>

            <CustomList 
            items={[uProject]}>
            </CustomList>
                {projects?.map((project) => (
                    <p>{project.projectName}</p>
                )) || error}
        </>
    )
}
    export default ProjectScreen
