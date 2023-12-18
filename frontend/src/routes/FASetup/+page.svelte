<script>
    import img from '$lib/assets/SadQrCode.svg'
    export let data
</script>

<div class="flex items-center justify-center h-screen">
    <div class="w-3/6 bg-white rounded-lg shadow-lg overflow-hidden">
        <div class="flex">
            {#if data?.qrCode}
            <img class="w-1/2" src="{data.qrCode}" alt="qrCode">
                {:else }
                <img class="w-1/2" src="{img}" alt="qrCode">
            {/if}
            <div class="p-4">
                {#if data?.error}
                    <span class="text-red-600">{data.error}</span>
                {/if}
            </div>
            {#if data.qrCode}
            <form method="post">
                <div class="p-4 pt-10">
                    <p class="text-xl">Scan the QR-Code via Smartphone. If you have done that, write it in down below.</p>
                    <hr>
                    <div class="mb-4 pt-5">
                        <label class="block text-gray-700 text-sm font-semibold mb-2">
                            First Token
                            <input placeholder="XXX-XXX" name="authToken" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500" type="password">
                        </label>
                    <button
                            class="mt-5 w-full bg-indigo-500 text-white py-2 rounded-md hover:bg-indigo-600 focus:outline-none"
                    >
                        Done
                    </button>

                </div>
            </form>
                {/if}


        </div>
    </div>
</div>