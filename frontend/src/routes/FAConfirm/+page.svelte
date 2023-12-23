<script>
    import img from '$lib/assets/SadQrCode.svg'
    import ButtonDone from "$lib/components/ButtonDone.svelte";
    import ButtonDone from "$lib/components/ButtonDone.svelte";

    export let form
</script>

<div class="flex items-center justify-center h-screen">
    <div class="w-3/6 bg-white rounded-lg shadow-lg overflow-hidden">

                <form method="post">
                    <div class="p-4 pt-10">
                        <p class="text-xl">Please enter the token from the authenticator app you have chosen here!</p>
                        <hr>
                        <div class="mb-4 pt-5">
                            <label class="block text-gray-700 text-sm font-semibold mb-2">
                                Your Token
                                <input placeholder="XXX-XXX" name="authToken" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500" type="text">
                            </label>
                            {#if form?.error}
                            <span class="text-red-600">{form.error}</span>
                            {/if}

                            <ButtonDone titel="Done"/>
                        </div>

                        </div>
                </form>
    </div>
</div>