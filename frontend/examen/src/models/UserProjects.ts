import type { Project } from "./Project"
import type { User } from "./User"

export interface UserProjects {
    users: User[]
    projects: Project[]
}