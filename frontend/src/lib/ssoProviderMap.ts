export const ssoProviderMap: Record<string, Provider> = {
    "google": {
        name: "Google"
    },
    "github": {
        name: "GitHub"
    }
}

type Provider = {
    name: string
}