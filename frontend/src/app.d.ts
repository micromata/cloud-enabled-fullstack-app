// See https://kit.svelte.dev/docs/types#app
// for information about these interfaces
declare global {
	namespace App {
		// interface Error {}
		interface Locals {
			user?: {
				id: number,
				username: string,
				email: string
			},
			token?: string
		}
		// interface PageData {}
		// interface Platform {}
	}
}

export {};
