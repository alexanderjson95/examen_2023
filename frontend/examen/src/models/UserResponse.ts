import type { User } from './User'

// framtida wrapper för response som har ytterligare fält för response relaterade svar

export interface UserResponse {
    user: User
    result: boolean
}